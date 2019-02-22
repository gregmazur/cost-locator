package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAddress(String streetAddress);

//    @Query(value = "select 1 from address a where exists (select * from address ia where ia.streetAddress = ?1)")
//    boolean exists(String streetAddress);
}
