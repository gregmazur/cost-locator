package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Identifier;
import org.open.budget.costlocator.entity.TenderIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TenderIssuerRepositoryTest {

    @Autowired
    private TenderIssuerRepository tenderIssuerRepository;

    @Before
    public void setUp() throws Exception {
        Identifier identifier = Identifier.builder().legalName("Name").id("id").scheme("scheme").build();
        Identifier identifier2 = Identifier.builder().legalName("Name").id("id2").scheme("scheme").build();
        tenderIssuerRepository.save(TenderIssuer.builder().name("some").name("Name").id("id").scheme("scheme").build());
        tenderIssuerRepository.save(TenderIssuer.builder().name("some").name("Name").id("id2").scheme("scheme").build());

    }

    @Test
    public void shouldBeTwoTenderIssuers(){
        assertEquals(tenderIssuerRepository.findAll().size(), 2);
    }

    @Test
    public void findByIdentifier_shouldReturn(){
        TenderIssuer tenderIssuer = tenderIssuerRepository.findById("id").get();
        assertNotNull(tenderIssuer);
    }

    @Test(expected = NoSuchElementException.class)
    public void findByIdentifier_shouldBeNull(){
        TenderIssuer tenderIssuer = tenderIssuerRepository.findById("wrong").get();
    }
}