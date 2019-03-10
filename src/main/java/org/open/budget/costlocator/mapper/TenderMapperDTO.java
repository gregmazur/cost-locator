package org.open.budget.costlocator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.open.budget.costlocator.dto.CityDTO;
import org.open.budget.costlocator.dto.RegionDTO;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;

@Mapper
public interface TenderMapperDTO {

    TenderMapperDTO INSTANCE = Mappers.getMapper(TenderMapperDTO.class);

    RegionDTO convertToDto(Region region);
    @Mapping(source = "region.id", target = "regionId")
    CityDTO convertToDto(City city);
}
