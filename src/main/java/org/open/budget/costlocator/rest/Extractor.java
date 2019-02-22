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

    private static final String REQUEST_LINK = "https://public.api.openprocurement.org";

    private static final String[] CLASSIFICATION_PREFIXES = {"44", "45", "507", "70", "71", "90"};

    private static String PATH = "/api/2.4/tenders";

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, Tender> cache;

    @Autowired
    private TenderService tenderService;

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
        TenderListWrapper tenderListWrapper = retrievePortion(PATH);
        while (tenderListWrapper.getNextPage() != null) {
            preLoadPortion(tenderListWrapper);
            PATH = tenderListWrapper.getNextPage().getPath();
            log.info("loaded list, path : {}", PATH);
            persistPortion(tenderListWrapper);
            tenderListWrapper = retrievePortion(PATH);
        }
    }

    private TenderListWrapper retrievePortion(String listPath) {
        return restTemplate.getForObject(REQUEST_LINK + listPath, TenderListWrapper.class);
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
                if (isNeededClassification(tender)){
                    log.info("UNSUITABLE CLASSIFICATION id {}",tender.getId());
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

    private Tender getLatestTenderImpl(TenderListItem item) {
        TenderWrapper tenderWrapper = restTemplate.getForObject("https://public.api.openprocurement.org/api/2.4/tenders/"
                + item.getId(), TenderWrapper.class);
        log.info("---REST RESPONSE---- FOR: " + item.getId());
        return tenderWrapper.getTender();
    }

    private Tender getLatestTender(TenderListItem item) {
        Tender tender = null;
        int i = 0;
        while (tender == null && i++ < 3) {
            try {
                tender = getLatestTenderImpl(item);
            } catch (ResourceAccessException e) {
                try {
                    Thread.sleep(1000);
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
