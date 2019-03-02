package org.open.budget.costlocator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.entity.Address;
import org.open.budget.costlocator.api.AddressAPI;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.repository.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TenderServiceImplTest {
    @Mock
    private TenderRepository tenderRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private StreetJPARepository streetRepository;
    @Mock
    private ClassificationRepository classificationRepository;
    @Mock
    private TenderIssuerRepository tenderIssuerRepository;
    @Mock
    private ListPathRepository listPathRepository;
    @InjectMocks
    private TenderServiceImpl tenderService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void formalize() {
    }

    @Test
    public void search() {
    }

    @Test
    public void getLastListPath() {
    }

    @Test
    public void getValidIndex_shouldPass() {
        String[] valid = {"12345", "someText01234,", "1234"};
        for (String test : valid)
            assertEquals(true, tenderService.getValidPostIndex(test).isPresent());
    }

    @Test
    public void getValidIndex_shouldNotPass() {
        String[] invalid = {"123", "someText234,", "0000", "00000", ".", "    "};
        for (String test : invalid)
            assertEquals(false, tenderService.getValidPostIndex(test).isPresent());
    }

    @Test
    public void getAdddresses_shouldPass() {
        AddressAPI addressAPI = AddressAPI.builder()
                .streetAddress("вул. Зарічна, 8 с. Кам'яний Брід  Коростишівського р-ну Житомирської обл.")
                .countryName("Україна")
                .locality("с. Кам'яний Брід  ")
                .postalCode("12511")
                .region("Житомирська область").build();
        Region region = Region.builder().name("житомирська").build();
        City city = City.builder().name("Кам'яний Брід").region(region).id(1L).build();
        Street street = Street.builder().city(city).name("Зарічна").id(1L).index("12511").build();
        Address mockAddress = Address.builder().street(street).houseNumber("8").city(city).build();
        when(addressRepository.find(eq(street.getCity().getId()),eq(street.getId()), eq("8"))).thenReturn(Optional.of(mockAddress));
        List<Address> addresses = tenderService.getAdddresses(addressAPI, Arrays.asList(street));
        verify(addressRepository,times(1)).find(eq(street.getCity().getId()),eq(street.getId()), eq("8"));
        assertEquals(1, addresses.size());
        Address address = addresses.get(0);
        assertEquals(street, address.getStreet());
        assertEquals("8", address.getHouseNumber());
    }

    @Test
    public void getAddressesWithoutStreetName_shouldPass() {
        AddressAPI addressAPI = AddressAPI.builder()
                .streetAddress("Місце надання Послуг - територія  Замовника: ВПК-1, ВПК-2, ВПК-3 ДП «ІЗМ МТП».")
                .countryName("Україна")
                .locality("м. Ізмаїл")
                .postalCode("68600")
                .region("Одеська область").build();
        Region region = Region.builder().name("одеська").build();
        City city = City.builder().name("Ізмаїл").region(region).id(33L).build();
        Street street = Street.builder().city(city).name("name").index("123").build();
        Address mockAddress = Address.builder().city(city).build();
        when(addressRepository.find(eq(city.getId()))).thenReturn(Optional.of(mockAddress));
        List<Address> addresses = tenderService.getAdddresses(addressAPI, Arrays.asList(street));
        verify(addressRepository, times(1)).find(eq(33L));
        assertEquals(1, addresses.size());
    }

    @Test
    public void getStreets_shouldNotPass() {
        String[] invalid = {"123", "someText234,", "0000", "00000", ".", "    "};
        for (String test : invalid)
            assertEquals(false, tenderService.getValidPostIndex(test).isPresent());
    }
}