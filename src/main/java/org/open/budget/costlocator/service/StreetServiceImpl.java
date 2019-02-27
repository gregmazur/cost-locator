package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.Street;
import org.open.budget.costlocator.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StreetServiceImpl implements StreetService {

    @Autowired
    private StreetRepository streetRepository;

    @Override
    @Transactional
    public void save(Street street) {
        if (streetRepository.exists(street.getRegion(), street.getCity(), street.getName()))
            return;
        streetRepository.save(street);
    }
}
