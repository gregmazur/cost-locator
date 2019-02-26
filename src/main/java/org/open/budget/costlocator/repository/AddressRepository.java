package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Address;
import org.open.budget.costlocator.api.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreet(Street street);

    List<Address> findByIndex(String index);

    @Query("SELECT a FROM Address a WHERE a.street = :street AND a.houseNumber = :hn AND a.index = :ind")
    Optional<Address> find(@Param("street") Street street, @Param("hn") String hn, @Param("ind") String index);

//    @Query(value = "select 1 from address a where exists (select * from address ia where ia.streetAddress = ?1)")
//    boolean exists(String streetAddress);
}
