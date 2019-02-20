package org.open.budget.costlocator.rest;

import org.open.budget.costlocator.api.Tender;
import org.open.budget.costlocator.api.TenderListItem;
import org.open.budget.costlocator.api.TenderListWrapper;
import org.open.budget.costlocator.api.TenderWrapper;
import org.open.budget.costlocator.repository.TenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Extractor {

    private static final Logger log = LoggerFactory.getLogger(Extractor.class);

    private String REQUEST_LINK = "https://public.api.openprocurement.org/api/2.4/tenders";

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, Tender> cache;

    private TenderListWrapper tenderListWrapper;

    @Autowired
    private TenderRepository tenderRepository;

    public void extract() {
        retrieve();
        preLoad(tenderListWrapper);
        log.info("loaded list");
        persist();
    }

    private void retrieve() {
        Map<String, String> params = new HashMap<>();
        params.put("offset", "2018-12-19T21%3A00%3A03.340891%2B03%3A00");
        tenderListWrapper = restTemplate.getForObject(REQUEST_LINK, TenderListWrapper.class, params);
    }

    private void preLoad(TenderListWrapper tenderListWrapper) {
        List<String> ids = tenderListWrapper.getTenderList().stream().map(t -> t.getId()).collect(Collectors.toList());
        cache = tenderRepository.findByIds(ids);
    }

    private void persist() {
        for (TenderListItem item : tenderListWrapper.getTenderList()) {
            Tender tender = cache.get(item.getId());
            if (tender == null || !tender.getDateModified().equals(item.getDateModified())) {
                tender = getLatestTender(item);
            }
            if (tender.getEntityId() == null) {
//                tenderRepository.(tender);
                log.info("saved new tender");
            }
        }
    }

    private Tender getLatestTender(TenderListItem item) {
        TenderWrapper tenderWrapper = restTemplate.getForObject(REQUEST_LINK + "/" + item.getId(), TenderWrapper.class);
        log.info("rest response ");
        return tenderWrapper.getTender();
    }
}
