package org.open.budget.costlocator.repository;

import org.open.budget.costlocator.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseTest {

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    protected StreetRepository streetRepository;

    @Autowired
    protected CityRepository cityRepository;


    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    protected RegionRepository regionRepository;

    protected Street street;

    protected City city;


    public void setUp() throws Exception {
        Region region = Region.builder().name("Odesa").build();
        region = regionRepository.save(region);
        regionRepository.flush();
        District district = District.builder().name("Odesa").region(region).build();
        district = districtRepository.save(district);
        districtRepository.flush();
        city = City.builder().name("Odessa").district(district).build();
        city = cityRepository.save(city);
        cityRepository.flush();
        street = Street.builder().city(city).name("Street").index("65091").build();
        street = streetRepository.save(street);
        streetRepository.flush();
    }
}
