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

    String whereQueryAddress = "id in (" +
            "select t.id from tender t " +
            "inner join tender_addresses ta on ta.tender_id=t.id " +
            "where ta.addresses_id= :address)";

    String whereQueryStreet = "id in (" +
            "select t.id from tender t " +
            "inner join tender_addresses ta on ta.tender_id=t.id " +
            "inner join address a on ta.addresses_id=a.id " +
            "where a.fk_street = :street)";

    String whereQueryCity = "id in (" +
            "select t.id from tender t " +
            "inner join tender_addresses ta on ta.tender_id=t.id " +
            "inner join address a on ta.addresses_id=a.id " +
            "where a.fk_city = :city)";

    List<Tender> findByTitle(String name);

    @Query(value = "select * from tender where " + whereQueryAddress, nativeQuery = true)
    List<Tender> findByAddress(@Param("address") Long address, Pageable pageable);

    @Query(value = "select count(*) from tender where " + whereQueryAddress, nativeQuery = true)
    Integer countFindByAddress(@Param("address") Long address);

    @Query(value = "select * from tender where " + whereQueryStreet, nativeQuery = true)
    List<Tender> findByStreet(@Param("street") Long street, Pageable pageable);

    @Query(value = "select count(*) from tender where " + whereQueryStreet, nativeQuery = true)
    Integer countFindByStreet(@Param("street") Long street);

    @Query(value = "select * from tender where " + whereQueryCity, nativeQuery = true)
    List<Tender> findByCity(@Param("city") Long city, Pageable pageable);

    @Query(value = "select count(id) from tender where " + whereQueryCity, nativeQuery = true)
    Integer countFindByCity(@Param("city") Long city);
}
