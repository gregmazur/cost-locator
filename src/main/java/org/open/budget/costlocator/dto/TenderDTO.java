package org.open.budget.costlocator.dto;

import lombok.Builder;

@Builder
public class TenderDTO {
    private String id;
    private String procurementMethod;
    private String status;
    private String owner;
    private String description;
    private String title;
    private String tenderID;
    private String date;
    private String  dateModified;
    //TenderDetail -> Amount
    private String currency;
    private Long amount;
    private Boolean valueAddedTaxIncluded;
    //TenderIssuer
    private String mfo;//TenderIssuer.id
    private String officeName;

    public TenderDTO(String id, String procurementMethod, String status, String owner, String description, String title,
                     String tenderID, String date, String dateModified, String currency, Long amount,
                     Boolean valueAddedTaxIncluded, String mfo, String officeName) {
        this.id = id;
        this.procurementMethod = procurementMethod;
        this.status = status;
        this.owner = owner;
        this.description = description;
        this.title = title;
        this.tenderID = tenderID;
        this.date = date;
        this.dateModified = dateModified;
        this.currency = currency;
        this.amount = amount;
        this.valueAddedTaxIncluded = valueAddedTaxIncluded;
        this.mfo = mfo;
        this.officeName = officeName;
    }
}
