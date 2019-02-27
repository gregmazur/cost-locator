package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Address;
import org.open.budget.costlocator.api.Address;
import org.open.budget.costlocator.api.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
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

    private Street street;

    @Before
    public void setUp() throws Exception {
        street = Street.builder().region("Odessa").name("Street").build();
        street = streetRepository.save(street);
        streetRepository.flush();
        Address address = Address.builder().index("65091").houseNumber("1").street(street).build();
        addressRepository.save(address);
        Address address2 = Address.builder().index("65091").houseNumber("2").street(street).build();
        addressRepository.save(address);
        addressRepository.save(address2);
        addressRepository.flush();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void notUniqueSave_shouldThrowError() {
        Address nonUniqueAddress =
                Address.builder().street(street).index("65091").houseNumber("1").build();
        addressRepository.save(nonUniqueAddress);
        addressRepository.flush();
    }

    @Test
    public void findByIndex_shouldReturn() {
        List<Address> address = addressRepository.findByIndex("65091");
        assertEquals(2, address.size());
    }

    @Test
    public void findByStreetName_shouldBeNull() {
        List<Address> address = addressRepository.findByIndex("null");
        assertEquals(true, address.isEmpty());
    }

    @Test
    public void findByStreet_shouldBeNull() {
        Optional<Address> address = addressRepository.find(street, "23", "151");
        assertEquals(false, address.isPresent());
    }

    @Test
    public void findByStreet_shouldNotBeNull() {
        Optional<Address> address = addressRepository.find(street, "1", "65091");
        assertEquals(true, address.isPresent());
    }
}