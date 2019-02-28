package org.open.budget.costlocator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.StreetReaderCsv;
import org.open.budget.costlocator.api.Street;
import org.open.budget.costlocator.service.StreetService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StreetReaderCsvTest {
    @Mock
    private StreetService streetService;
    @InjectMocks
    private StreetReaderCsv serviceCsv;

    @Before
    public void startUp(){

    }

    @Test
    public void start() {
        serviceCsv.start();
        verify(streetService,times(1)).save(ArgumentMatchers.any(Street.class)
//                Street.builder().region("вінницька").city("Бар").name("Андрія Малишка").build()
        );
    }

    @Test
    public void getStreetTest() {
        String[] csvRecs = "Вінницька;Барський;м. Бар;23000;вул. Андрія Малишка;1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16;".split(";");
        Street street = serviceCsv.getStreet(csvRecs).get();
        assertEquals("вінницька", street.getRegion());
        assertEquals("Бар", street.getCity());
        assertEquals("Андрія Малишка", street.getName());
    }

    @Test
    public void getStreet2Test() {
        String[] csvRecs = "Дніпропетровська;;м. Дніпро;49069;вул. Саксаганського;27,29,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,52,54,56,58,60;".split(";");
        Street street = serviceCsv.getStreet(csvRecs).get();
        assertEquals("дніпропетровська", street.getRegion());
        assertEquals("Дніпро", street.getCity());
        assertEquals("Саксаганського", street.getName());
    }

    @Test
    public void getStreetWhereStreetNull() {
        String[] csvRecs = "Дніпропетровська;;м. Дніпро;49069;;;".split(";");
        Optional<Street> street = serviceCsv.getStreet(csvRecs);
        assertEquals(false, street.isPresent());
    }
}