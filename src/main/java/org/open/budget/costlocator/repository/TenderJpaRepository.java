package org.open.budget.costlocator.repository;

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

    @Query(value = "select * from tender where id in (" +
            "select t.id from tender t " +
            "inner join tender_addresses ta on ta.tender_id=t.id " +
            "where ta.addresses_id= :address)", nativeQuery = true)
    List<Tender> findByAddress(@Param("address") Long address, Pageable pageable);

    @Query(value = "select * from tender where id in (" +
            "select t.id from tender t " +
            "inner join tender_addresses ta on ta.tender_id=t.id " +
            "inner join address a on ta.addresses_id=a.id " +
            "where a.fk_street = :street)", nativeQuery = true)
    List<Tender> findByStreet(@Param("street") Long street, Pageable pageable);

    @Query(value = "select * from tender where id in (" +
            "select t.id from tender t " +
            "inner join tender_addresses ta on ta.tender_id=t.id " +
            "inner join address a on ta.addresses_id=a.id " +
            "where a.fk_city = :city)", nativeQuery = true)
    List<Tender> findByCity(@Param("city") Long city, Pageable pageable);
}
