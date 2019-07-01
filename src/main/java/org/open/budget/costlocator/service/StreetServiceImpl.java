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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.MANDATORY;
import static javax.transaction.Transactional.TxType.NEVER;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Service
@Builder
@AllArgsConstructor
public class StreetServiceImpl implements StreetService {

    @PersistenceContext
    private EntityManager em;

    private Set<Region> regions = new HashSet<>();

    @PostConstruct
    @Transactional
    void warmUp() {
        regions = new HashSet<>(em.createQuery("select r from Region r").getResultList());
    }

    @Override
    @Transactional
    public void save(List<Street> streets) {
        for (Street street : streets) {
            saveImpl(street);
        }
    }

    @Override
    @Transactional
    public Street save(Street street) {
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
        Region region = regions.stream().filter(r -> r.equals(regionTemp)).findFirst().orElse(null);

        if (region == null) {
            region = Region.builder().fullName(regionTemp.getFullName()).name(regionTemp.getName())
                    .districts(new HashSet<>()).build();
            em.persist(region);
        }
        regions.add(region);
        return region;
    }

    District saveIfNeeded(District district, Region region) {
        Optional<District> cityFromDB = region.getDistrict(district);
        if (!cityFromDB.isPresent()) {
            district = District.builder().name(district.getName()).fullName(district.getFullName())
                    .region(region).cities(new HashSet<>()).build();
            em.persist(district);
            region.addDistrict(district);
            return district;
        }
        return cityFromDB.get();
    }

    City saveIfNeeded(City city, District district) {
        Optional<City> cityFromDB = district.getCity(city);
        if (!cityFromDB.isPresent()) {
            city = City.builder().name(city.getName()).fullName(city.getFullName()).district(district).build();
            em.persist(city);
            district.addCity(city);
            return city;
        }
        return cityFromDB.get();
    }

    Street saveIfNeeded(Street street, City city) {
        Optional<Street> streetFromDB = city.getStreet(street);
        if (!streetFromDB.isPresent()) {
            street = Street.builder().name(street.getName()).fullName(street.getFullName()).index(street.getIndex()).city(city).build();
            em.persist(street);
            city.addStreet(street);
            return street;
        }
        return streetFromDB.get();
    }
}
