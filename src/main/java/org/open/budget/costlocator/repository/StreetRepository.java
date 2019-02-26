package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    List<Street> findByRegionContainingIgnoreCase(String region);

    List<Street> findByCityContainingIgnoreCase(String city);

    @Query("SELECT DISTINCT s.region FROM Street s")
    Set<String> getRegions();

    Street findByName(String name);
}
