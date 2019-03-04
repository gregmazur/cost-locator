package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreetJPARepository extends JpaRepository<Street, Long> {

    List<Street> findByIndex(String index);

    @Query("select s from Street s where s.city.id = :#{#city.id} and s.name = :name and s.index = :index")
    Optional<Street> find(@Param("city") City city, @Param("name") String name, @Param("index") String index);
}
