package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.Street;
import org.springframework.stereotype.Service;

@Service
public interface StreetService {
    void save(Street street);
}
