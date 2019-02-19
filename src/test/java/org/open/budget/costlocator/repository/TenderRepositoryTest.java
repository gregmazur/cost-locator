package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Tender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.profiles.active=test")
public class TenderRepositoryTest {

    @Autowired
    private TenderRepository tenderRepository;

    @Test
    public void test(){
        Tender tender = Tender.newBuilder().withProzzorroId("prozzorroID").withTitle("Title").build();
        tenderRepository.save(tender);
        List<Tender> tendersFromDb = tenderRepository.findByTitle("Title");
        assertEquals(tendersFromDb.size(), 1);
    }
}