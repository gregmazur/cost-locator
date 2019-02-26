package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StreetRepositoryTest {
    @Autowired
    private StreetRepository streetRepository;

    @Before
    public void setUp() throws Exception {
        Street street = Street.builder().region("Odessa").name("Street").build();
        streetRepository.save(street);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void notUniqueSave_shouldThrowError(){
        Street street = Street.builder().region("Odessa").name("Street").build();
        streetRepository.save(street);
        streetRepository.flush();
    }

//    @Test
//    public void findByStreetName_shouldReturn(){
//        Optional<Street> street = streetRepository.findByStreet(street);
//        assertEquals(street.isPresent(), true);
//    }
//
//    @Test
//    public void findByStreetName_shouldBeNull(){
//        Optional<Street> address = streetRepository.findByStreet(Street.builder().name("s").region("1").build());
//        assertEquals(address.isPresent(), false);
//    }
}