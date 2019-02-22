package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(properties = "spring.profiles.active=IT")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Before
    public void setUp() throws Exception {
        Address address = Address.builder().countryName("UA").streetAddress("unique").postalCode("65091").build();
        addressRepository.save(address);
        Address address2 = Address.builder().countryName("UA").streetAddress("secondunique").postalCode("65091").build();
        addressRepository.save(address);
        addressRepository.save(address2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void notUniqueSave_shouldThrowError(){
        Address nonUniqueAddress =
                Address.builder().countryName("UA").streetAddress("unique").postalCode("65091").build();
        addressRepository.save(nonUniqueAddress);
        addressRepository.flush();
    }

    @Test
    public void findByStreetName_shouldReturn(){
        Address address = addressRepository.findByStreetAddress("unique").get();
        assertNotNull(address);
    }

    @Test(expected = NoSuchElementException.class)
    public void findByStreetName_shouldBeNull(){
        Address address = addressRepository.findByStreetAddress("wrong").get();
    }
}