package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.ApplicationProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationPropertyRepository extends JpaRepository<ApplicationProperty,String> {
}
