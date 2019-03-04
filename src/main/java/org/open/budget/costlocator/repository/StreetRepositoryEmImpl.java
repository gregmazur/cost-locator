package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class StreetRepositoryEmImpl implements StreetRepositoryEm {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Street> findByRegion(Region region) {
        Query query = entityManager.createNativeQuery("select * from street s " +
                "join city c on c.id=s.fk_city " +
                "where c.fk_region=?", Street.class);
        query.setParameter(1, region.getId());
        return query.getResultList();
    }
}
