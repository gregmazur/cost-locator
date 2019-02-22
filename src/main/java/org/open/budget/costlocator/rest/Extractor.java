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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Extractor {

    private static final Logger log = LoggerFactory.getLogger(Extractor.class);

    private final String REQUEST_LINK = "https://public.api.openprocurement.org";

    private String PATH = "/api/2.4/tenders";

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, Tender> cache;

    @Autowired
    private TenderService tenderService;

    private void setUp(){
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
            log.info("loaded list");
            persistPortion(tenderListWrapper);
            retrievePortion(tenderListWrapper.getNextPage().getPath());
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
                    log.info("NO DELIVERY ADRESS",tender);
                    continue;
                }
                if (tender.getStatus().equals("unsuccessful") || tender.getStatus().equals("cancelled")){
                    log.info("CANCELED or UNSUCCESSFUL",tender);
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

    private Tender getLatestTender(TenderListItem item) {
        TenderWrapper tenderWrapper = restTemplate.getForObject(REQUEST_LINK + PATH + "/" + item.getId(), TenderWrapper.class);
        log.info("---REST RESPONSE---- FOR: " + item.getId());
        return tenderWrapper.getTender();
    }
}
