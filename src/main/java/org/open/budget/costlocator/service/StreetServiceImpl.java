package org.open.budget.costlocator.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.District;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.repository.CityRepository;
import org.open.budget.costlocator.repository.DistrictRepository;
import org.open.budget.costlocator.repository.RegionRepository;
import org.open.budget.costlocator.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Builder
@AllArgsConstructor
public class StreetServiceImpl implements StreetService {

    private Set <Region> regions = new HashSet<>();

    private Collection<Region> regionsToSave = new LinkedHashSet<>();
//    private Collection<District> districtsToSave = new LinkedList<>();
//    private Collection<City> citiesToSave = new LinkedList<>();
//    private Collection<Street> streetsToSave = new LinkedList<>();

//    @Autowired
//    private StreetRepository streetRepository;
//    @Autowired
//    private CityRepository cityRepository;
//    @Autowired
//    private DistrictRepository districtRepository;
    @Autowired
    private RegionRepository regionRepository;

    @PostConstruct
    void warmUp() {
        regions = new HashSet<>(regionRepository.findAll());
    }

    @Override
    @Transactional
    public void save(List<Street> streets) {
        for (Street street : streets) {
            saveImpl(street);
        }
//        streetRepository.saveAll(streetsToSave);
//        streetsToSave.clear();
        regionRepository.saveAll(regionsToSave);
        regionsToSave.clear();
    }

    @Override
    @Transactional
    public Street save(Street street) {
//        streetRepository.saveAll(streetsToSave);
        regionRepository.saveAll(regionsToSave);
        regionsToSave.clear();
        return saveImpl(street);
    }

    @Override
    public Collection<Region> getRegions() {
        return regions;
    }

    Street saveImpl(Street street) {
        Region region = saveIfNeeded(street.getCity().getDistrict().getRegion());
        District district = saveIfNeeded(street.getCity().getDistrict(), region);
        City city = saveIfNeeded(street.getCity(), district);
        return saveIfNeeded(street, city);
    }

    Region saveIfNeeded(Region regionTemp) {
        String regionName = regionTemp.getName();
        Optional<Region> region = regions.stream().filter(r -> r.equals(regionName)).findFirst();
        if (!region.isPresent()) {
            region = regionRepository.findByName(regionName);
            if (!region.isPresent()) {
                region = Optional.of(Region.builder().fullName(regionTemp.getFullName()).name(regionTemp.getName())
                        .districts(new HashSet<>()).build());

            }
            regions.add(region.get());
        }
        regionsToSave.add(region.get());
        return region.get();
    }

    District saveIfNeeded(District district, Region region) {
        Optional<District> cityFromDB = region.getDistrict(district);
        if (!cityFromDB.isPresent()) {
            district = District.builder().name(district.getName()).fullName(district.getFullName())
                    .region(region).cities(new HashSet<>()).build();
//            districtsToSave.add(district);
            region.addDistrict(district);
            return district;
        }
        return cityFromDB.get();
    }

    City saveIfNeeded(City city, District district) {
        Optional<City> cityFromDB = district.getCity(city);
        if (!cityFromDB.isPresent()) {
            city = City.builder().name(city.getName()).fullName(city.getFullName()).district(district).build();
//            citiesToSave.add(city);
            district.addCity(city);
            return city;
        }
        return cityFromDB.get();
    }

    Street saveIfNeeded(Street street, City city) {
        Optional<Street> streetFromDB = city.getStreet(street);
        if (!streetFromDB.isPresent()) {
            street = Street.builder().name(street.getName()).fullName(street.getFullName()).index(street.getIndex()).city(city).build();
//            streetsToSave.add(street);
            city.addStreet(street);
            return street;
        }
        return streetFromDB.get();
    }
}
