package org.open.budget.costlocator.service;

import org.open.budget.costlocator.dto.*;
import org.open.budget.costlocator.entity.*;
import org.open.budget.costlocator.mapper.TenderMapperDTO;
import org.open.budget.costlocator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:4200")
public class WebServiceImpl implements WebService {

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TenderRepository tenderRepository;

//    private List<Region> cache; TODO


    @Override
    public Collection<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream().map(TenderMapperDTO.INSTANCE::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CityDTO> getCitiesByRegionId(Number regionId) {
        Optional<Region> region = regionRepository.findById(regionId.longValue());
        if (!region.isPresent())
            return Collections.EMPTY_LIST;
        return region.get().getCities().stream().map(TenderMapperDTO.INSTANCE::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Collection<StreetDTO> getStreetsByCityId(Number cityId) {
        Optional<City> city = cityRepository.findById(cityId.longValue());
        if (!city.isPresent())
            return Collections.EMPTY_LIST;
        return city.get().getStreets().stream().map(TenderMapperDTO.INSTANCE::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Collection<AddressDTO> getAddressesByStreetId(Number streetId) {
        Optional<Street> street = streetRepository.findById(streetId.longValue());
        if (!street.isPresent())
            return Collections.EMPTY_LIST;
        return street.get().getAddresses().stream().map(TenderMapperDTO.INSTANCE::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Collection<TenderDTO> getTendersBySearchCriteria(SearchCriteria searchCriteria) {
        Pageable pageable = of(0, 20);
        List <Tender> tenders;
        Long address = searchCriteria.getAddress();
        Long street = searchCriteria.getStreet();
        Long city = searchCriteria.getCity();
        if (address != null) {
            tenders = tenderRepository.findByAddress(address, pageable);
        } else if (street != null) {
            tenders = tenderRepository.findByStreet(street, pageable);
        } else if (city != null) {
            tenders = tenderRepository.findByCity(city, pageable);
        } else {
            throw new IllegalStateException("not able to find tenders without at least cityId");
        }
        return tenders.stream()
                .map(TenderMapperDTO.INSTANCE::convertToDto).collect(Collectors.toList());
    }
}
