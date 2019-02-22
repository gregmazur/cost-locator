package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.Tender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TenderService {

    Tender save(Tender tender);

    void flush();

    Map<String,Tender> search(SearchCriteria searchCriteria);
}