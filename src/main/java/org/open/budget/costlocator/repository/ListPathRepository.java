package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.ListPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListPathRepository extends JpaRepository<ListPath,Long> {
}
