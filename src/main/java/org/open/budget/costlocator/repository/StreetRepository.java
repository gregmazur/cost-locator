package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    List<Street> findByIndex(String index);

    @Query("select s from Street s where s.city.id = :#{#city.id} and s.name = :name and s.index = :index")
    Optional<Street> find(@Param("city") City city, @Param("name") String name, @Param("index") String index);

    @Query(value = "select * from street s " +
            "inner join city c on c.id=s.fk_city " +
            "where c.fk_region= :#{#region.id}", nativeQuery = true)
    Collection<Street> findByRegion(@Param("region") Region region);
}
