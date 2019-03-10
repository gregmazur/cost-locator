package org.open.budget.costlocator.dto;

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
    private String name;
}
