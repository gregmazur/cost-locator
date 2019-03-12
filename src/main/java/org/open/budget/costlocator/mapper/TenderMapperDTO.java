package org.open.budget.costlocator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.open.budget.costlocator.dto.AddressDTO;
import org.open.budget.costlocator.dto.CityDTO;
import org.open.budget.costlocator.dto.RegionDTO;
import org.open.budget.costlocator.dto.StreetDTO;
import org.open.budget.costlocator.entity.Address;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;

@Mapper
public interface TenderMapperDTO {

    TenderMapperDTO INSTANCE = Mappers.getMapper(TenderMapperDTO.class);

    RegionDTO convertToDto(Region region);
    @Mapping(source = "region.id", target = "regionId")
    CityDTO convertToDto(City city);
    @Mapping(source = "city.id", target = "cityId")
    StreetDTO convertToDto(Street street);
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "street.id", target = "streetId")
    AddressDTO convertToDto(Address address);
}
