package org.open.budget.costlocator.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.open.budget.costlocator.api.*;
import org.open.budget.costlocator.entity.*;

@Mapper
public interface TenderMapper {

    TenderMapper INSTANCE = Mappers.getMapper(TenderMapper.class);

    Classification classificationAPIToClassification(ClassificationAPI classificationAPI);

    DeliveryLocation deliveryLocationAPIToDeliveryLocation(DeliveryLocationApi deliveryLocationApi);

    DeliveryDate deliveryDateAPIToDeliveryDate(DeliveryDateAPI deliveryDateAPI);

    Unit unitApiToUnit(UnitAPI unitAPI);

    default Tender tenderApiToTender(TenderAPI tenderAPI) {
        Item item = tenderAPI.getItems().get(0);
        TenderIssuerAPI tenderIssuerAPI = tenderAPI.getIssuer();
        TenderIssuer tenderIssuer = TenderIssuer.builder().name(tenderIssuerAPI.getName()).kind(tenderIssuerAPI.getKind())
                .id(tenderIssuerAPI.getIdentifier().getId()).legalName(tenderIssuerAPI.getIdentifier().getLegalName())
                .scheme(tenderIssuerAPI.getIdentifier().getScheme()).build();
        TenderDetail tenderDetail = TenderDetail.builder()
                .deliveryDate(deliveryDateAPIToDeliveryDate(item.getDeliveryDateAPI())).id(item.getItemId())
                .quantity(item.getQuantity()).awardCriteria(tenderAPI.getAwardCriteria()).description(item.getItemDescription())
                .unit(unitApiToUnit(item.getUnit())).procurementMethodType(tenderAPI.getProcurementMethodType())
                .submissionMethod(tenderAPI.getSubmissionMethod()).value(tenderAPI.getValue()).build();
        return Tender.builder().id(tenderAPI.getId()).title(tenderAPI.getTitle()).date(tenderAPI.getDate())
                .dateModified(tenderAPI.getDateModified()).description(tenderAPI.getDescription())
                .issuer(tenderIssuer)
                .owner(tenderAPI.getOwner()).procurementMethod(tenderAPI.getProcurementMethod())
                .status(tenderAPI.getStatus()).tenderID(tenderAPI.getTenderID())
                .classification(classificationAPIToClassification(item.getClassification()))
                .deliveryLocation(deliveryLocationAPIToDeliveryLocation(item.getDeliveryLocation()))
                .tenderDetail(tenderDetail).build();
    }
}
