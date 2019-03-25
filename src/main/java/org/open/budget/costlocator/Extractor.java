package org.open.budget.costlocator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.api.TenderListItem;
import org.open.budget.costlocator.api.TenderListWrapper;
import org.open.budget.costlocator.api.TenderWrapper;
import org.open.budget.costlocator.entity.Item;
import org.open.budget.costlocator.entity.Tender;
import org.open.budget.costlocator.entity.UnsuccessfulItem;
import org.open.budget.costlocator.service.SearchCriteria;
import org.open.budget.costlocator.service.TenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Extractor {

    static final String API_LINK = "https://public.api.openprocurement.org";

    static final String TENDER_LINK = API_LINK + "/api/2.4/tenders/";

    private static final String[] CLASSIFICATION_PREFIXES = {"44", "45", "507", "70", "71", "90"};

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, Tender> cache;

    private TenderService tenderService;

    public Extractor(Map<String, Tender> cache, TenderService tenderService, RestTemplate restTemplate) {
        this.cache = cache;
        this.tenderService = tenderService;
        this.restTemplate = restTemplate;
    }

    public Extractor(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    public Extractor() {
    }

    private void setUp() {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        Gson gson = new GsonBuilder().create();
        gsonHttpMessageConverter.setGson(gson);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(gsonHttpMessageConverter);
        SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
        rf.setReadTimeout(10000);
        rf.setConnectTimeout(10000);
        restTemplate = new RestTemplate(rf);
        restTemplate.setMessageConverters(messageConverters);
    }

    public void extract() {
        log.warn("------RETRIEVING FROM API HAS STARTED-----");
        setUp();
        String path = tenderService.getLastListPath();
        TenderListWrapper tenderListWrapper = retrievePortion(path);
        while (!tenderListWrapper.getTenderList().isEmpty()) {
            preLoadPortion(tenderListWrapper);
            path = tenderListWrapper.getNextPage().getPath();
            log.info("loaded list, path : {}", path);
            persistPortion(tenderListWrapper.getTenderList());
            tenderService.save(path);
            tenderListWrapper = retrievePortion(path);
        }
        persistPortion(tenderService.getAllUnsuccessful());
        log.warn("-----EXTRACTION FINISHED--------");
    }

    private TenderListWrapper retrievePortion(String listPath) {
        TenderListWrapper wrapper = null;
        int i = 0;
        while (wrapper == null && i++ < 3) {
            try {
                wrapper = restTemplate.getForObject(API_LINK + listPath, TenderListWrapper.class);
                ;
            } catch (Exception e) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                log.warn("ResourceAccessException attempt " + i + " for " + listPath, e);
            }
        }
        if (wrapper == null)
            throw new ResourceAccessException("unable to get response");
        return wrapper;
    }

    private void preLoadPortion(TenderListWrapper tenderListWrapper) {
        List<String> ids = tenderListWrapper.getTenderList().stream().map(t -> t.getId()).collect(Collectors.toList());
        cache = tenderService.search(SearchCriteria.builder().tenderIds(ids).build());
    }

    private void persistPortion(List<? extends Item> items) {
        for (Item item : items) {
            Tender tender = cache.get(item.getId());
            if (tender == null) {
                tryToCreateTender(item);
            } else {
                if (item instanceof TenderListItem) {
                    if (!tender.getDateModified().equals(((TenderListItem) item).getDateModified())) {
                        tenderService.save(getLatestTender(item));
                    }
                } else {
                    tryToCreateTender(item);
                }
            }
        }
        cache.clear();
//        tenderService.flush();
    }

    private void tryToCreateTender(Item item) {
        TenderAPI tenderApi = getLatestTender(item);
        if (tenderApi.getItemAPIS().get(0).getDeliveryAddress() == null || tenderApi.getIssuer().getAddress() == null) {
            log.info("NO DELIVERY ADRESS id {}", tenderApi.getId());
            return;
        }
        if (tenderApi.getStatus().equals("unsuccessful") || tenderApi.getStatus().equals("cancelled")) {
            log.info("CANCELED or UNSUCCESSFUL id {}", tenderApi.getId());
            return;
        }
        if (!isNeededClassification(tenderApi)) {
            log.info("UNSUITABLE CLASSIFICATION id {}", tenderApi.getId());
            return;
        }
        if (tenderService.save(tenderApi) != null)
            log.info("-----CREATED NEW TENDER----- {}", tenderApi.getId());
    }

    private boolean isNeededClassification(TenderAPI tender) {
        for (String prefix : CLASSIFICATION_PREFIXES) {
            if (tender.getItemAPIS().get(0).getClassification().getId().startsWith(prefix))
                return true;
        }
        return false;
    }

    TenderAPI getLatestTenderImpl(Item item) {
        log.info("---REST REQUEST---- FOR: {}", item.getId());
        TenderWrapper tenderWrapper = restTemplate.getForObject(TENDER_LINK + item.getId(), TenderWrapper.class);
        return tenderWrapper.getTender();
    }

    TenderAPI getLatestTender(Item item) {
        TenderAPI tender = null;
        int i = 0;
        while (tender == null && i++ < 3) {
            try {
                tender = getLatestTenderImpl(item);
            } catch (Exception e) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                log.warn("ResourceAccessException attempt " + i + " for " + item.getId(), e);
            }
        }
        if (tender == null)
            throw new ResourceAccessException("unable to get response");
        return tender;
    }
}
