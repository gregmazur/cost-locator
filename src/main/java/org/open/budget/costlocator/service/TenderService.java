package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.Tender;
import org.springframework.stereotype.Service;

@Service
public interface TenderService {

    Tender create(Tender tender);

    Tender update(Tender newTender);

    Tender search(SearchCriteria searchCriteria);
}
