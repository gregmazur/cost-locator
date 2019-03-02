package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetJPARepository extends JpaRepository<Street, Long> {

    List<Street> findByIndex(String index);
}
