package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Tender;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomTenderRepository {
    Map<String, Tender> findByIds(List<String> ids);
}
