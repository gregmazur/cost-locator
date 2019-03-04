package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;

import java.util.List;
import java.util.Optional;

public interface StreetRepositoryEm {

    List<Street> findByRegion(Region region);
}
