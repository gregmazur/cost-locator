package org.open.budget.costlocator.service;

import org.open.budget.costlocator.entity.ListPath;
import org.open.budget.costlocator.entity.Tender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TenderService {

    Tender save(Tender tender);

    String getLastListPath();

    ListPath save(String path);

    Map<String,Tender> search(SearchCriteria searchCriteria);
}
