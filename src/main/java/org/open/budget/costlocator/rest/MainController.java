package org.open.budget.costlocator.rest;

import org.open.budget.costlocator.dto.*;
import org.open.budget.costlocator.service.SearchCriteria;
import org.open.budget.costlocator.service.TenderService;
import org.open.budget.costlocator.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.data.domain.PageRequest.of;

@RestController
public class MainController {

    @Autowired
    private WebService webService;

    @GetMapping("/regions")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<RegionDTO> getRegions() {
        return webService.getAllRegions();
    }

    @GetMapping("/regions/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<CityDTO> getCities(@PathVariable("id") Long id) {
        return webService.getCitiesByRegionId(id);
    }

    @GetMapping("/districts/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<StreetDTO> getStreets(@PathVariable("id") Long id) {
        return webService.getStreetsByCityId(id);
    }

    @GetMapping("/streets/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<AddressDTO> getAddresses(@PathVariable("id") Long id) {
        return webService.getAddressesByStreetId(id);
    }

    @PostMapping(value = "/search", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public TenderSearchResultDTO getTenders(@RequestBody SearchCriteria searchCriteria) {
//        if (searchCriteria == null || searchCriteria.getCity())
//            return Collections.EMPTY_LIST;

        return webService.getTendersBySearchCriteria(searchCriteria);
    }
}
