package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Tender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenderRepository extends JpaRepository<Tender, Long> {

    List<Tender> findByTitle(String name);

//    List<Tender> findByDeliveryAddress(DeliveryAddress deliveryAddress);
}
