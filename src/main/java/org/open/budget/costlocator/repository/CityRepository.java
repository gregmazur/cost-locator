package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c where c.region.id = ?1 and c.name = ?2")
    City findByRegionIdAndName(Long id, String name);
}
