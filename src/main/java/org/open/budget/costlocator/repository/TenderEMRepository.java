package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Tender;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TenderEMRepository {
    Map<String, Tender> findByIds(List<String> ids);
}
