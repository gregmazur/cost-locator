package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.UnsuccessfulItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnsuccessfulItemRepository extends JpaRepository<UnsuccessfulItem, Long> {
}
