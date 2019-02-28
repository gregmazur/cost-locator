package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StreetRepositoryTest {
    @Autowired
    private StreetRepository streetRepository;

    @Before
    public void setUp() throws Exception {
        Street street = Street.builder().region("Kyivska").city("Kyiv").name("Kreshatik").build();
        streetRepository.save(street);
        Street street2 = Street.builder().region("Kyivska").city("Kyiv").name("Andriyvki uzviz").build();
        streetRepository.save(street2);
        Street street3 = Street.builder().region("Mykolaivska").city("Mykolaiv").name("Golovna").build();
        streetRepository.save(street3);
        Street street4 = Street.builder().region("вінницька").city("Калинівка").name("Саксаганського").build();
        streetRepository.save(street4);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void notUniqueSave_shouldThrowError(){
        Street street = Street.builder().region("Kyivska").city("Kyiv").name("Kreshatik").build();
        streetRepository.save(street);
        streetRepository.flush();
    }

    @Test
    public void findByRegionContainingIgnoreCase_shouldReturn(){
        List<Street> streets = streetRepository.findByRegionContainingIgnoreCase("kyivska");
        assertEquals(2, streets.size());
    }

    @Test
    public void findByRegionContainingIgnoreCase_shouldReturnNull(){
        List<Street> streets = streetRepository.findByRegionContainingIgnoreCase("Odeska");
        assertEquals(0, streets.size());
    }

    @Test
    public void getRegions_ShouldReturn2(){
        Set<String> regions = streetRepository.getRegions();
        assertEquals(3, regions.size());
    }

    @Test
    public void exists_shouldReturn(){
        boolean exists = streetRepository.exists("Kyivska", "Kyiv", "Kreshatik");
        assertEquals(true, exists);
    }

    @Test
    public void exists_shouldReturnNull(){
        boolean exists = streetRepository.exists("Kyivska", "Kyiv", "");
        assertEquals(false, exists);
    }

    @Test
    public void existsUA_shouldReturnNull(){
        boolean exists = streetRepository.exists("дніпропетровська", "Дніпро", "Саксаганського");
        assertEquals(false, exists);
    }
}