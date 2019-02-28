package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.api.Address;
import org.open.budget.costlocator.api.Street;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Address> findByIndex(String index) {
        return entityManager.createNativeQuery();
    }

    @Override
    public Optional<Address> find(Street street, String hn, String index) {
        return Optional.empty();
    }

    @Override
    public boolean exists(String streetAddress) {
        return false;
    }
}
