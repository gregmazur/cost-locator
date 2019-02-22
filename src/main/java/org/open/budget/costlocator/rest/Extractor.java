package org.open.budget.costlocator.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.open.budget.costlocator.api.*;
import org.open.budget.costlocator.service.SearchCriteria;
import org.open.budget.costlocator.service.TenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Extractor {

    private static final Logger log = LoggerFactory.getLogger(Extractor.class);

    static final String API_LINK = "https://public.api.openprocurement.org";

    static final String TENDER_LINK = API_LINK + "/api/2.4/tenders/";

    private static final String[] CLASSIFICATION_PREFIXES = {"44", "45", "507", "70", "71", "90"};

    static String path =
            "/api/2.4/tenders"
            ;

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, Tender> cache;

    @Autowired
    private TenderService tenderService;

    public Extractor(RestTemplate restTemplate, Map<String, Tender> cache, TenderService tenderService) {
        this.restTemplate = restTemplate;
        this.cache = cache;
        this.tenderService = tenderService;
    }

    public Extractor() {
    }

    private void setUp() {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        Gson gson = new GsonBuilder().registerTypeAdapter(Tender.class, new TenderDeserializer())
                .create();
        gsonHttpMessageConverter.setGson(gson);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(gsonHttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
    }

    public void extract() {
        setUp();
        TenderListWrapper tenderListWrapper = retrievePortion(path);
        while (tenderListWrapper.getNextPage() != null) {
            preLoadPortion(tenderListWrapper);
            String path = tenderListWrapper.getNextPage().getPath();
            log.info("loaded list, path : {}", path);
            persistPortion(tenderListWrapper);
            tenderListWrapper = retrievePortion(path);
        }
    }

    private TenderListWrapper retrievePortion(String listPath) {
        return restTemplate.getForObject(API_LINK + listPath, TenderListWrapper.class);
    }

    private void preLoadPortion(TenderListWrapper tenderListWrapper) {
        List<String> ids = tenderListWrapper.getTenderList().stream().map(t -> t.getId()).collect(Collectors.toList());
        cache = tenderService.search(SearchCriteria.builder().tenderIds(ids).build());
    }

    private void persistPortion(TenderListWrapper tenderListWrapper) {
        for (TenderListItem item : tenderListWrapper.getTenderList()) {
            Tender tender = cache.get(item.getId());
            if (tender == null) {
                tender = getLatestTender(item);
                if (tender.getItem().getDeliveryAddress() == null) {
                    log.info("NO DELIVERY ADRESS id {}", tender.getId());
                    continue;
                }
                if (tender.getStatus().equals("unsuccessful") || tender.getStatus().equals("cancelled")) {
                    log.info("CANCELED or UNSUCCESSFUL id {}", tender.getId());
                    continue;
                }
                if (!isNeededClassification(tender)){
                    log.info("UNSUITABLE CLASSIFICATION id {}",tender.getId());
                    continue;
                }
                tenderService.save(tender);
                log.info("-----CREATED NEW TENDER-----", tender);
            }
            if (!tender.getDateModified().equals(item.getDateModified())) {
                tender = getLatestTender(item);
                tenderService.save(tender);
            }
        }
        cache.clear();
//        tenderService.flush();
    }

    private boolean isNeededClassification(Tender tender){
        for (String prefix : CLASSIFICATION_PREFIXES) {
            if (tender.getItem().getClassification().getId().startsWith(prefix))
                return true;
        }
        return false;
    }

    Tender getLatestTenderImpl(TenderListItem item) {
        log.info("---REST REQUEST---- FOR: {}", item.getId());
        TenderWrapper tenderWrapper = restTemplate.getForObject(TENDER_LINK + item.getId(), TenderWrapper.class);
        return tenderWrapper.getTender();
    }

    Tender getLatestTender(TenderListItem item) {
        Tender tender = null;
        int i = 0;
        while (tender == null && i++ < 3) {
            try {
                tender = getLatestTenderImpl(item);
            } catch (ResourceAccessException e) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                log.warn("ResourceAccessException attempt " + i, e);
            }
        }
        if (tender == null)
            throw new ResourceAccessException("unable to get response");
        return tender;
    }
}
