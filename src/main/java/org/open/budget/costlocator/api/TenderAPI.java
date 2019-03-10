
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class TenderAPI {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("procurementMethod")
    @Expose
    private String procurementMethod;
    @SerializedName("procurementMethodType")
    @Expose
    private String procurementMethodType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("items")
    private List<ItemAPI> itemAPIS;
    @SerializedName("value")
    @Expose
    private ValueAPI value;
    @SerializedName("submissionMethod")
    @Expose
    private String submissionMethod;
    @SerializedName("procuringEntity")
    @Expose
    private TenderIssuerAPI issuer;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("tenderID")
    @Expose
    private String tenderID;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("dateModified")
    @Expose
    private Date dateModified;
    @SerializedName("awardCriteria")
    @Expose
    private String awardCriteria;
    @SerializedName("awards")
    @Expose
    private List<Award> awards;

    public TenderAPI() {
    }

    public TenderAPI(String id, String procurementMethod, String procurementMethodType, String status,
                     String description, String title, List<ItemAPI> itemAPIS, ValueAPI value, String submissionMethod,
                     TenderIssuerAPI issuer, String owner, String tenderID, Date date, Date dateModified,
                     String awardCriteria, List<Award> awards) {
        this.id = id;
        this.procurementMethod = procurementMethod;
        this.procurementMethodType = procurementMethodType;
        this.status = status;
        this.description = description;
        this.title = title;
        this.itemAPIS = itemAPIS;
        this.value = value;
        this.submissionMethod = submissionMethod;
        this.issuer = issuer;
        this.owner = owner;
        this.tenderID = tenderID;
        this.date = date;
        this.dateModified = dateModified;
        this.awardCriteria = awardCriteria;
        this.awards = awards;
    }
}
