package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Address;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a where a.city.id = :city AND a.street.id = :street AND a.houseNumber = :hn")
    Optional<Address> find(@Param("city") Long city, @Param("street") Long street, @Param("hn") String houseNumber);

    @Query("select a from Address a where s.city.id = :cityId and s.name = 'N/A'")
    Optional<Address> findNA(@Param("cityId") Long cityId);
}
