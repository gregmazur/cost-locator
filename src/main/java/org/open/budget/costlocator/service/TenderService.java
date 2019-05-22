package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.entity.ApplicationProperty;
import org.open.budget.costlocator.entity.Tender;
import org.open.budget.costlocator.entity.UnsuccessfulItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TenderService {

    Tender save(TenderAPI tender);

    String getProperty(String id);

    ApplicationProperty saveProp(String id, String path);

    Map<String,Tender> search(SearchCriteria searchCriteria);

    List<UnsuccessfulItem> getAllUnsuccessful();
}
