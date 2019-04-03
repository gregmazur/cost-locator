package org.open.budget.costlocator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.open.budget.costlocator.api.*;
import org.open.budget.costlocator.entity.*;

import java.util.ArrayList;
import java.util.HashSet;

@Mapper
public interface TenderMapperAPI {

    TenderMapperAPI INSTANCE = Mappers.getMapper(TenderMapperAPI.class);

    Classification classificationAPIToClassification(ClassificationAPI classificationAPI);

    DeliveryLocation deliveryLocationAPIToDeliveryLocation(DeliveryLocationApi deliveryLocationApi);

    DeliveryDate deliveryDateAPIToDeliveryDate(DeliveryDateAPI deliveryDateAPI);

    Unit unitApiToUnit(UnitAPI unitAPI);

    default Value valueApiToValue(ValueAPI valueAPI){
        return Value.builder().amount(valueAPI.getAmount().longValue()).currency(valueAPI.getCurrency())
                .valueAddedTaxIncluded(valueAPI.getValueAddedTaxIncluded()).build();
    }

    default Tender tenderApiToTender(TenderAPI tenderAPI) {
        ItemAPI item = tenderAPI.getItemAPIS().get(0);
        TenderIssuerAPI tenderIssuerAPI = tenderAPI.getIssuer();
        ValueAPI valueAPI = tenderAPI.getValue() != null ? tenderAPI.getValue() :
                tenderAPI.getAwards().get(0).getValue();
        TenderIssuer tenderIssuer = TenderIssuer.builder().name(tenderIssuerAPI.getName()).kind(tenderIssuerAPI.getKind())
                .id(tenderIssuerAPI.getIdentifier().getId()).legalName(tenderIssuerAPI.getIdentifier().getLegalName())
                .scheme(tenderIssuerAPI.getIdentifier().getScheme()).build();
        TenderDetail tenderDetail = TenderDetail.builder()
                .deliveryDate(deliveryDateAPIToDeliveryDate(item.getDeliveryDateAPI())).id(item.getItemId())
                .quantity(item.getQuantity() != null ? item.getQuantity().longValue() : null).awardCriteria(tenderAPI.getAwardCriteria()).description(item.getItemDescription())
                .unit(unitApiToUnit(item.getUnit())).procurementMethodType(tenderAPI.getProcurementMethodType())
                .submissionMethod(tenderAPI.getSubmissionMethod())
                .value(valueApiToValue(valueAPI)).build();
        return Tender.builder().id(tenderAPI.getId()).title(tenderAPI.getTitle()).date(tenderAPI.getDate())
                .dateModified(tenderAPI.getDateModified()).description(tenderAPI.getDescription())
                .issuer(tenderIssuer)
                .owner(tenderAPI.getOwner()).procurementMethod(tenderAPI.getProcurementMethod())
                .status(tenderAPI.getStatus()).tenderID(tenderAPI.getTenderID())
                .classification(classificationAPIToClassification(item.getClassification()))
                .deliveryLocation(deliveryLocationAPIToDeliveryLocation(item.getDeliveryLocation()))
                .addresses(new HashSet<>())
                .tenderDetail(tenderDetail).build();
    }
}
