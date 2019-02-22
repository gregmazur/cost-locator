package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.api.Identifier;
import org.open.budget.costlocator.api.TenderIssuer;
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
        tenderIssuerRepository.save(TenderIssuer.builder().name("some").identifier(identifier).build());
        tenderIssuerRepository.save(TenderIssuer.builder().name("some").identifier(identifier2).build());

    }

    @Test
    public void shouldBeTwoTenderIssuers(){
        assertEquals(tenderIssuerRepository.findAll().size(), 2);
        Identifier identifier = Identifier.builder().legalName("Name").id("id2").scheme("scheme").build();
        tenderIssuerRepository.save(TenderIssuer.builder().name("some").identifier(identifier).build());
        tenderIssuerRepository.flush();
        assertEquals(tenderIssuerRepository.findAll().size(), 2);
    }

    @Test
    public void findByIdentifier_shouldReturn(){
        TenderIssuer tenderIssuer = tenderIssuerRepository.findByIdentifier(
                Identifier.builder().legalName("Name").id("id2").scheme("scheme").build()).get();
        assertNotNull(tenderIssuer);
    }

    @Test(expected = NoSuchElementException.class)
    public void findByIdentifier_shouldBeNull(){
        TenderIssuer tenderIssuer = tenderIssuerRepository.findByIdentifier(
                Identifier.builder().legalName("Name").id("Wrong").scheme("scheme").build()).get();
    }
}