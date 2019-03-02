package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification,String> {
}
