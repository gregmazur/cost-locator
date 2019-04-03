package org.open.budget.costlocator.service;

import lombok.Builder;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.repository.CityRepository;
import org.open.budget.costlocator.repository.RegionRepository;
import org.open.budget.costlocator.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Builder
public class StreetServiceImpl implements StreetService {

    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private RegionRepository regionRepository;

    public StreetServiceImpl(StreetRepository streetRepository, CityRepository cityRepository, RegionRepository regionRepository) {
        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    @Transactional
    public void save(Region region, City city, Street street) {
        region = saveIfNeeded(region);
        city = saveIfNeeded(city, region);
        street = saveIfNeeded(street, city);
    }

    Region saveIfNeeded(Region region){
        Region regionFromDB = regionRepository.findByName(region.getName());
        if (regionFromDB == null) {
            return regionRepository.save(region);
        }
        return regionFromDB;
    }

    City saveIfNeeded(City city, Region region){
        City cityFromDB = cityRepository.findByRegionIdAndName(region.getId(), city.getName());
        if (cityFromDB == null){
            city = City.builder().name(city.getName()).region(region).build();
            return cityRepository.save(city);
        }
        return cityFromDB;
    }

    Street saveIfNeeded(Street street, City city){
        Optional<Street> streetFromDB = streetRepository.find(city, street.getName(), street.getFullName(), street.getIndex());
        if (!streetFromDB.isPresent()){
            street = Street.builder().name(street.getName()).fullName(street.getFullName()).index(street.getIndex()).city(city).build();
            return streetRepository.save(street);
        }
        return streetFromDB.get();
    }
}
