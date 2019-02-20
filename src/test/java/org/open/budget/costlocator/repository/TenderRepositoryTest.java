package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Tender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
//        (properties = "spring.profiles.active=test")
public class TenderRepositoryTest {

    @Autowired
    private TenderRepository tenderRepository;

    private Tender tender1, tender2, tender3;

    @Before
    public void setUp(){
        Tender tender1 = Tender.newBuilder().withProzzorroId("prozzorroID").withTitle("Title").build();
        tenderRepository.save(tender1);
        Tender tender2 = Tender.newBuilder().withProzzorroId("prozzorroID2").withTitle("Title2").build();
        tenderRepository.save(tender2);
        Tender tender3 = Tender.newBuilder().withProzzorroId("prozzorroID3").withTitle("Title3").build();
        tenderRepository.save(tender3);
    }

    @Test
    public void findByTitleSuccessTest(){
        List<Tender> tendersFromDb = tenderRepository.findByTitle("Title");
        assertEquals(tendersFromDb.size(), 1);
    }

    @Test
    public void findByTitleEmptyResult(){
        List<Tender> tendersFromDb = tenderRepository.findByTitle("WRONGTITLE");
        assertEquals(tendersFromDb.size(), 0);
    }

    @Test
    public void findByIdsSuccessTest(){
        Map<String, Tender> tenders = tenderRepository.findByIds(Arrays.asList("prozzorroID", "prozzorroID2"));
        assertEquals(tenders.size(), 2);
    }

    @Test
    public void findByIdsEmptyTest(){
        Map<String, Tender> tenders = tenderRepository.findByIds(Arrays.asList("wrongId", "WrongId2"));
        assertEquals(tenders.size(), 0);
    }

//    @After
//    public void destroy(){
//        tenderRepository.delete(tender1);
//        tenderRepository.delete(tender2);
//        tenderRepository.delete(tender3);
//    }


}