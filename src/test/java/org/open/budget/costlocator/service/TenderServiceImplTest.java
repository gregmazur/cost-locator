package org.open.budget.costlocator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.api.Street;
import org.open.budget.costlocator.api.Tender;
import org.open.budget.costlocator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TenderServiceImplTest {
    @Mock
    private TenderRepository tenderRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private StreetRepository streetRepository;
    @Mock
    private ClassificationRepository classificationRepository;
    @Mock
    private TenderIssuerRepository tenderIssuerRepository;
    @Mock
    private ListPathRepository listPathRepository;
    @InjectMocks
    private TenderServiceImpl tenderService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void formalize() {
    }

    @Test
    public void search() {
    }

    @Test
    public void getLastListPath() {
    }

    @Test
    public void getValidIndex_shouldPass() {
        String[] valid = {"12345", "someText01234,", "1234"};
        for (String test : valid)
            assertEquals(true, tenderService.getValidPostIndex(test).isPresent());
    }

    @Test
    public void getValidIndex_shouldNotPass() {
        String[] invalid = {"123", "someText234,", "0000", "00000", ".", "    "};
        for (String test : invalid)
            assertEquals(false, tenderService.getValidPostIndex(test).isPresent());
    }

    @Test
    public void getStreets_shouldPass() {

    }

    @Test
    public void getStreets_shouldNotPass() {
        String[] invalid = {"123", "someText234,", "0000", "00000", ".", "    "};
        for (String test : invalid)
            assertEquals(false, tenderService.getValidPostIndex(test).isPresent());
    }
}