package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "select count(s)>0 from Street s where " +
            "s.region = :sregion and s.city = :scity and s.name = :sname")
    boolean exists(@Param("sregion") String region, @Param("scity") String city, @Param("sname") String name);
}
