package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.DeliveryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocation,Long> {
}
