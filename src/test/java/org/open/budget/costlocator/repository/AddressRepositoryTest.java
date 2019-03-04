package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.entity.Address;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(properties = "spring.profiles.active=IT")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegionRepository regionRepository;

    private Street street;

    private City city;

    @Before
    public void setUp() throws Exception {
        Region region = Region.builder().name("Odessa").build();
        region = regionRepository.save(region);
        city = City.builder().name("Odessa").region(region).build();
        city = cityRepository.save(city);
        street = Street.builder().city(city).name("Street").index("65091").build();
        street = streetRepository.save(street);
        streetRepository.flush();
        Address address = Address.builder().houseNumber("1").city(city).street(street).build();
        addressRepository.save(address);
        Address address2 = Address.builder().houseNumber("2").city(city).street(street).build();
        addressRepository.save(address2);
        Address addressNoStreet = Address.builder().city(city).build();
        addressRepository.save(addressNoStreet);
        addressRepository.flush();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void notUniqueSaveAddress_shouldThrowError() {
        Address nonUniqueAddress =
                Address.builder().city(city).street(street).houseNumber("1").build();
        addressRepository.save(nonUniqueAddress);
        addressRepository.flush();
    }

//    @Test(expected = DataIntegrityViolationException.class)
//    public void notUniqueSaveAddressWithNoStreet_shouldThrowError() {
//        Address nonUniqueAddress =
//                Address.builder().city(city).build();
//        addressRepository.save(nonUniqueAddress);
//        addressRepository.flush();
//    }

    @Test(expected = DataIntegrityViolationException.class)
    public void notUniqueSaveStreet_shouldThrowError() {
        Street nonUniqueStreet =
                Street.builder().city(city).name("Street").index("65091").build();
        streetRepository.save(nonUniqueStreet);
        streetRepository.flush();
    }

    @Test
    public void findByIndex_shouldReturn() {
        List<Street> streets = streetRepository.findByIndex("65091");
        assertEquals(1, streets.size());
    }

    @Test
    public void findByStreetName_shouldBeNull() {
        List<Street> streets = streetRepository.findByIndex("null");
        assertEquals(true, streets.isEmpty());
    }

    @Test
    public void findByStreet_shouldBeNull() {
        Optional<Address> address = addressRepository.find(street.getCity().getId(), street.getId(), "151");
        assertEquals(false, address.isPresent());
    }

    @Test
    public void findByStreet_shouldNotBeNull() {
        Optional<Address> address = addressRepository.find(street.getCity().getId(), street.getId(), "1");
        assertEquals(true, address.isPresent());
    }
}