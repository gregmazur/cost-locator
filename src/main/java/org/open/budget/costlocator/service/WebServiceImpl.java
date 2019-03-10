package org.open.budget.costlocator.service;

import org.open.budget.costlocator.dto.*;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.mapper.TenderMapperDTO;
import org.open.budget.costlocator.repository.CityRepository;
import org.open.budget.costlocator.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:4200")
public class WebServiceImpl implements WebService {

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CityRepository cityRepository;


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
        return null;
    }

    @Override
    public Collection<AddressDTO> getAddressesByStreetId(Number streetId) {
        return null;
    }

    @Override
    public Collection<TenderDTO> getTendersBySearchCriteria(SearchCriteria searchCriteria) {
        return null;
    }
}
