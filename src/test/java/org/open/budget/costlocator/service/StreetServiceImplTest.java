package org.open.budget.costlocator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.District;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.repository.CityRepository;
import org.open.budget.costlocator.repository.DistrictRepository;
import org.open.budget.costlocator.repository.RegionRepository;
import org.open.budget.costlocator.repository.StreetRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class StreetServiceImplTest {
//
//    @Mock
//    private Map<String,Region> regionMap;
//    @Mock
//    private StreetRepository streetRepository;
//    @Mock
//    private CityRepository cityRepository;
//    @Mock
//    private DistrictRepository districtRepository;
//    @Mock
//    private RegionRepository regionRepository;
//    @InjectMocks
//    private StreetServiceImpl streetService;
//
//
//    @Before
//    public void setUp() throws Exception {
//        regionMap = new HashMap<>();
//    }

//    @Test
//    public void save() {
//        Region region = Region.builder().name("kyiv").fullName("Kyiv").districts(new HashSet<>()).id(1L).build();
//        District district = District.builder().name("kyiv").fullName("Kyiv").region(region).build();
//        City city = City.builder().name("kyiv").fullName("Kyiv").district(district).streets(new HashSet<>()).build();
//        Street street = Street.builder().city(city).name("street").fullName("Street").index("65091").build();
//
//        when(regionRepository.save(region)).thenReturn(region);
//        when(districtRepository.save(district)).thenReturn(district);
//        when(cityRepository.save(city)).thenReturn(city);
//        when(streetRepository.save(street)).thenReturn(street);
//
//        streetService.save(street);
//        verify(streetRepository, times(1)).save(eq(street));
//        verify(regionRepository, times(1)).save(eq(region));
//        verify(cityRepository, times(1)).save(eq(city));
//    }
}