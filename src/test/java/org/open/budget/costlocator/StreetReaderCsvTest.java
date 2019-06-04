package org.open.budget.costlocator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.service.StreetService;
import org.open.budget.costlocator.service.TenderService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StreetReaderCsvTest {
    @Mock
    private StreetService streetService;
    @Mock
    private TenderService tenderService;
    @InjectMocks
    private StreetReaderCsv serviceCsv;

    @Before
    public void startUp(){

    }

//    @Test
//    public void start() {
//        StreetReaderCsv streetReaderCsv = new StreetReaderCsv(streetService, tenderService, getClass().getResource("/houses-test.csv").getPath());
//        streetReaderCsv.start();
//        verify(streetService,times(1)).save(any(LStreet.class)
////                Street.builder().region("вінницька").city("Бар").officeName("Андрія Малишка").build()
//        );
//    }

    @Test
    public void getStreetTest() {
        String[] csvRecs = "Вінницька;Барський;м. Бар;23000;вул. Андрія Малишка;1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16;".split(";");
        Street wrapper = serviceCsv.getStreet(csvRecs).get();
        assertEquals("вінницька", wrapper.getCity().getDistrict().getRegion().getName());
        assertEquals("барський", wrapper.getCity().getDistrict().getName());
        assertEquals("бар", wrapper.getCity().getName());
        assertEquals("андрія малишка", wrapper.getName());
    }

    @Test
    public void getStreet2Test() {
        String[] csvRecs = "Дніпропетровська;;м. Дніпро;49069;вул. Саксаганського;27,29,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,52,54,56,58,60;".split(";");
        Street wrapper = serviceCsv.getStreet(csvRecs).get();
        assertEquals("дніпропетровська", wrapper.getCity().getDistrict().getRegion().getName());
        assertEquals("дніпро", wrapper.getCity().getDistrict().getName());
        assertEquals("дніпро", wrapper.getCity().getName());
        assertEquals("саксаганського", wrapper.getName());
    }

    @Test
    public void getStreetWhereStreetNull() {
        String[] csvRecs = "Дніпропетровська;;м. Дніпро;49069;;;".split(";");
        assertEquals(false, serviceCsv.getStreet(csvRecs).isPresent());
    }
}