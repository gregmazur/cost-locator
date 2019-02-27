package org.open.budget.costlocator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.api.Street;
import org.open.budget.costlocator.repository.StreetRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StreetReaderServiceCsvTest {
    @Mock
    private StreetService streetService;
    @InjectMocks
    private StreetReaderServiceCsv serviceCsv;

    @Before
    public void startUp(){

    }

    @Test
    public void start() {
        serviceCsv.start();
        verify(streetService).save(ArgumentMatchers.any(Street.class)
//                Street.builder().region("вінницька").city("Бар").name("Андрія Малишка").build()
        );
    }

    @Test
    public void getStreetTest() {
        String[] csvRecs = "Вінницька;Барський;м. Бар;23000;вул. Андрія Малишка;1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16;".split(";");
        Street street = serviceCsv.getStreet(csvRecs);
        assertEquals("вінницька", street.getRegion());
        assertEquals("Бар", street.getCity());
        assertEquals("Андрія Малишка", street.getName());
    }
}