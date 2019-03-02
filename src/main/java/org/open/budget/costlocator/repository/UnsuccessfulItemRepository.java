package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.UnsuccessfulItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UnsuccessfulItemRepository extends JpaRepository<UnsuccessfulItem, Long> {
}
