package org.open.budget.costlocator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.repository.CityRepository;
import org.open.budget.costlocator.repository.RegionRepository;
import org.open.budget.costlocator.repository.StreetRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StreetServiceImplTest {

    @Mock
    private StreetRepository streetRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private RegionRepository regionRepository;
    @InjectMocks
    private StreetServiceImpl streetService;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void save() {
        Region region = Region.builder().name("Kyiv").id(1L).build();
        City city = City.builder().name("Kyiv").region(region).build();
        Street street = Street.builder().city(city).name("Street").index("65091").build();

        when(regionRepository.findByName("Kyiv")).thenReturn(null);
        when(regionRepository.save(region)).thenReturn(region);
        when(cityRepository.findByRegionIdAndName(any(), eq("Kyiv"))).thenReturn(null);
        when(cityRepository.save(city)).thenReturn(city);
        when(streetRepository.find(city, "Street", "65091")).thenReturn(Optional.empty());

        streetService.save(region,city,street);
        verify(streetRepository, times(1)).save(eq(street));
        verify(regionRepository, times(1)).save(eq(region));
        verify(cityRepository, times(1)).save(eq(city));
    }
}