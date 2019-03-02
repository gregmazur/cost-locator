package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.Tender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TenderEMRepositoryImpl implements TenderEMRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String,Tender> findByIds(List<String> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tender> query = cb.createQuery(Tender.class);
        Root<Tender> tenderRoot = query.from(Tender.class);

        Path<String> emailPath = tenderRoot.get("id");

        List<Predicate> predicates = new ArrayList<>();
        for (String id : ids) {
            predicates.add(cb.like(emailPath, id));
        }
        query.select(tenderRoot)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList().stream().collect(
                Collectors.toMap(t -> t.getId(), x -> x));
    }
}
