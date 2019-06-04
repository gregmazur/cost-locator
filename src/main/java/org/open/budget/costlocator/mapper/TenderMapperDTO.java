package org.open.budget.costlocator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.open.budget.costlocator.dto.*;
import org.open.budget.costlocator.entity.*;

@Mapper
public interface TenderMapperDTO {

    TenderMapperDTO INSTANCE = Mappers.getMapper(TenderMapperDTO.class);

    RegionDTO convertToDto(Region region);

    @Mapping(source = "district.fullName", target = "district")
    CityDTO convertToDto(City city);

    @Mapping(source = "city.id", target = "cityId")
    StreetDTO convertToDto(Street street);

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "street.id", target = "streetId")
    AddressDTO convertToDto(Address address);

    @Mapping(source = "date", dateFormat = "dd.MM.yyyy", target = "date")
    @Mapping(source = "dateModified", dateFormat = "dd.MM.yyyy", target = "dateModified")
    @Mapping(source = "tenderDetail.value.currency", target = "currency")
    @Mapping(source = "tenderDetail.value.amount", target = "amount")
    @Mapping(source = "tenderDetail.value.valueAddedTaxIncluded", target = "valueAddedTaxIncluded")
    @Mapping(source = "issuer.id", target = "mfo")
    @Mapping(source = "issuer.name", target = "officeName")
    TenderDTO convertToDto(Tender tender);
}
