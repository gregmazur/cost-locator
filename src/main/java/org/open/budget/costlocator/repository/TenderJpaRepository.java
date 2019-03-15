package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Address;
import org.open.budget.costlocator.entity.Tender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenderJpaRepository extends JpaRepository<Tender, Long> {
    List<Tender> findByTitle(String name);

    List<Tender> findByTenderID(String name);

    @Query(value = "select * from Tender t " +
            "inner join tender_addresses ta on ta.tenderId=t.id " +
            "where ta.addresses_id= :address", nativeQuery = true)
    List<Tender> findByAddress(@Param("address") Long address, Pageable pageable);
}
