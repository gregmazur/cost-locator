package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenderJpaRepository extends JpaRepository<Tender, Long> {
    List<Tender> findByTitle(String name);

    List<Tender> findByTenderID(String name);
}
