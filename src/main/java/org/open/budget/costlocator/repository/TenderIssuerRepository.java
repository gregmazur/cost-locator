package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Identifier;
import org.open.budget.costlocator.api.TenderIssuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenderIssuerRepository extends JpaRepository<TenderIssuer, Identifier> {

    Optional<TenderIssuer> findByIdentifier(Identifier identifier);
}
