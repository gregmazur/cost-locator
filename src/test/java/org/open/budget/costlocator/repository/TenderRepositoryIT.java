package org.open.budget.costlocator.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Tender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=IT")
public class TenderRepositoryIT {

    @Autowired
    private TenderRepository tenderRepository;

    private Tender tender;

    @Before
    public void setUp(){
        tender = Tender.builder().id("prozzorroID").title("TestTitle!").build();
        tenderRepository.save(tender);
    }

    @Test
    public void test(){
        List<Tender> tendersFromDb = tenderRepository.findByTitle("TestTitle!");
        assertEquals(tendersFromDb.size(), 1);
    }

    @Test
    public void emptyResult(){
        List<Tender> tendersFromDb = tenderRepository.findByTitle("WRONGTITLE");
        assertEquals(tendersFromDb.size(), 0);
    }

    @After
    public void destroy(){
        tenderRepository.delete(tender);
    }
}