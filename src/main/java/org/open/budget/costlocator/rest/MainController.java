package org.open.budget.costlocator.rest;

import org.open.budget.costlocator.dto.*;
import org.open.budget.costlocator.service.SearchCriteria;
import org.open.budget.costlocator.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

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

    @GetMapping("/cities/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<StreetDTO> getStreets(@PathVariable("id") Long id) {
        return webService.getStreetsByCityId(id);
    }

    @GetMapping("/streets/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<AddressDTO> getAddresses(@PathVariable("id") Long id) {
        return webService.getAddressesByStreetId(id);
    }

    @PostMapping("/search")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<TenderDTO> getTenders(@RequestBody SearchCriteria searchCriteria) {
        if (searchCriteria == null || searchCriteria.getCity())
            return Collections.EMPTY_LIST;
        return webService.getTendersBySearchCriteria(searchCriteria);
    }
}
