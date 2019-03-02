package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.entity.Classification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(properties = "spring.profiles.active=IT")
public class ClassificationRepositoryTest {

    @Autowired
    private ClassificationRepository classificationRepository;

    @Before
    public void setUp() throws Exception {
        Classification classification = Classification.builder().id("id1").description("desc").build();
        Classification classification2 = Classification.builder().id("id2").description("desc").build();
        classificationRepository.save(classification);
        classificationRepository.save(classification2);
    }

//    @Test(expected = DataIntegrityViolationException.class)
//    public void notUniqueSave_shouldThrowError(){
//        Classification nonUniqueClassification = Classification.builder().id("id1").description("desc").build();
//        classificationRepository.save(nonUniqueClassification);
//        classificationRepository.flush();
//    }

    @Test
    public void findById_shouldReturn(){
        Classification classification = classificationRepository.findById("id1").get();
        assertNotNull(classification);
    }

    @Test(expected = NoSuchElementException.class)
    public void findById_shouldBeNull(){
        Classification classification = classificationRepository.findById("wrong").get();
    }
}