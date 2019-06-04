package org.open.budget.costlocator.service;

import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface StreetService {

    void save(List<Street> street);

    Street save(Street street);

    Collection<Region> getRegions();
}
