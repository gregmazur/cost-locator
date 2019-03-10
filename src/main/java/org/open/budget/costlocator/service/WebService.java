package org.open.budget.costlocator.service;

import org.open.budget.costlocator.dto.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface WebService {

    Collection<RegionDTO> getAllRegions();

    Collection<CityDTO> getCitiesByRegionId(Number regionId);

    Collection<StreetDTO> getStreetsByCityId(Number cityId);

    Collection<AddressDTO> getAddressesByStreetId(Number streetId);

    Collection<TenderDTO> getTendersBySearchCriteria(SearchCriteria searchCriteria);
}
