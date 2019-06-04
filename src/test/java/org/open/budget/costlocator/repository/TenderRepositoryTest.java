package org.open.budget.costlocator.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.open.budget.costlocator.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
//        (properties = "spring.profiles.active=test")
public class TenderRepositoryTest extends BaseTest {

    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private RegionRepository regionRepository;

    private Tender tender1, tender2, tender3;

    private Address address, address2, addressNoStreet;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        address = Address.builder().houseNumber("1").city(city).street(street).build();
        addressRepository.save(address);
        address2 = Address.builder().houseNumber("2").city(city).street(street).build();
        addressRepository.save(address2);
        addressNoStreet = Address.builder().city(city).build();
        addressRepository.save(addressNoStreet);
        addressRepository.flush();
        Tender tender1 = Tender.builder().id("prozzorroID").title("Title").addresses(new HashSet<>(Arrays.asList(address))).build();
        tenderRepository.save(tender1);

        Tender tender2 = Tender.builder().id("prozzorroID2").title("Title2")
                .addresses(new HashSet<>(Arrays.asList(address,address2,addressNoStreet))).build();
        tenderRepository.save(tender2);

        Tender tender3 = Tender.builder().id("prozzorroID3").title("Title3")
                .addresses(new HashSet<>(Arrays.asList(address2))).build();
        tenderRepository.save(tender3);
        tenderRepository.flush();

//        tender1.getAddresses().add(address);
//        tender2.getAddresses().add(address);
//        tender2.getAddresses().add(address2);
//        tender2.getAddresses().add(addressNoStreet);
//        tender3.getAddresses().add(address2);
//        tenderRepository.flush();
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

    @Test
    public void findByAddressTest(){
        List<Tender> tenders = tenderRepository.findByAddress(address.getId(), PageRequest.of(0,10));
        assertEquals(2, tenders.size());

    }

    @Test
    public void findByAddressTestWithPage1(){
        List<Tender> tenders = tenderRepository.findByAddress(address.getId(), PageRequest.of(0,1));
        assertEquals(1, tenders.size());
    }

    @Test
    public void findByStreet(){
        List<Tender> tenders = tenderRepository.findByStreet(street.getId(), PageRequest.of(0,10));
        assertEquals(3, tenders.size());
    }

    @Test
    public void findByCity(){
        List<Tender> tenders = tenderRepository.findByCity(city.getId(), PageRequest.of(0,10));
        assertEquals(3, tenders.size());
    }

//    @After
//    public void destroy(){
//        tenderRepository.delete(tender1);
//        tenderRepository.delete(tender2);
//        tenderRepository.delete(tender3);
//    }


}