package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.entity.ListPath;
import org.open.budget.costlocator.entity.Tender;
import org.open.budget.costlocator.entity.UnsuccessfulItem;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public interface TenderService {

    Tender save(TenderAPI tender);

    String getLastListPath();

    ListPath save(String path);

    Map<String,Tender> search(SearchCriteria searchCriteria);

    List<UnsuccessfulItem> getAllUnsuccessful();
}
