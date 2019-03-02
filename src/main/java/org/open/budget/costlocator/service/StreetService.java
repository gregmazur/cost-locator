package org.open.budget.costlocator.service;

import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.springframework.stereotype.Service;

@Service
public interface StreetService {
    void save(Region region, City city, Street street);
}
