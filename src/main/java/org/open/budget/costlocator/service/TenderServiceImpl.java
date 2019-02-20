package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.Tender;
import org.open.budget.costlocator.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenderServiceImpl implements TenderService{

    @Autowired
    TenderRepository tenderRepository;

    @Override
    public Tender create(Tender tender) {
        return null;
    }

    @Override
    public Tender update(Tender newTender) {
        return null;
    }

    @Override
    public Tender search(SearchCriteria searchCriteria) {
        return null;
    }
}
