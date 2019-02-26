
package org.open.budget.costlocator.api;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "tender")
@Builder
@Getter
public class Tender {
    @Id
    @SerializedName("id")
    @Expose
    @Column(length = 32)
    private String id;

    @SerializedName("procurementMethod")
    @Expose
    private String procurementMethod;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tenderPeriod")
    @Expose
    @Transient
    private TenderPeriod tenderPeriod;
    @SerializedName("documents")
    @Expose
    @Transient
    private List<Document> documents = null;
    @SerializedName("numberOfBids")
    @Expose
    private Long numberOfBids;
    @SerializedName("description")
    @Expose
    @Column(columnDefinition ="text" )
    private String description;
    @SerializedName("title")
    @Expose
    @Column(columnDefinition ="text")
    private String title;
    @SerializedName("minimalStep")
    @Expose
    @Transient
    private MinimalStep minimalStep;
    @SerializedName("items")
    @Transient
    private List<Item> items;//for deserialization
    @Embedded
    private Item item;
    @SerializedName("procurementMethodType")
    @Expose
    private String procurementMethodType;
    @SerializedName("value")
    @Expose
    @Embedded
    private Value value;
    @SerializedName("submissionMethod")
    @Expose
    private String submissionMethod;
    @SerializedName("procuringEntity")
    @Expose
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "fk_issuer")
    private TenderIssuer issuer;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("tenderID")
    @Expose
    @Column(length = 32)
    private String tenderID;
    @SerializedName("enquiryPeriod")
    @Expose
    @Transient
    private EnquiryPeriod enquiryPeriod;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("guarantee")
    @Expose
    @Transient
    private Guarantee guarantee;
    @SerializedName("dateModified")
    @Expose
    private Date dateModified;
    @SerializedName("awardCriteria")
    @Expose
    private String awardCriteria;
    @ManyToMany
    private List<Address> addresses;

    public Tender() {
    }

    public Tender(String id, String procurementMethod, String status, TenderPeriod tenderPeriod, List<Document> documents,
                  Long numberOfBids, String description, String title, MinimalStep minimalStep, List<Item> items, Item item,
                  String procurementMethodType, Value value, String submissionMethod, TenderIssuer issuer, String owner,
                  String tenderID, EnquiryPeriod enquiryPeriod, Date date, Guarantee guarantee, Date dateModified,
                  String awardCriteria, List<Address> addresses) {
        this.id = id;
        this.procurementMethod = procurementMethod;
        this.status = status;
        this.tenderPeriod = tenderPeriod;
        this.documents = documents;
        this.numberOfBids = numberOfBids;
        this.description = description;
        this.title = title;
        this.minimalStep = minimalStep;
        this.items = items;
        this.item = item;
        this.procurementMethodType = procurementMethodType;
        this.value = value;
        this.submissionMethod = submissionMethod;
        this.issuer = issuer;
        this.owner = owner;
        this.tenderID = tenderID;
        this.enquiryPeriod = enquiryPeriod;
        this.date = date;
        this.guarantee = guarantee;
        this.dateModified = dateModified;
        this.awardCriteria = awardCriteria;
        this.addresses = addresses;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public void setIssuer(TenderIssuer issuer) {
        this.issuer = issuer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tender)) return false;
        Tender tender = (Tender) o;
        return getId().equals(tender.getId()) &&
                getTenderID().equals(tender.getTenderID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTenderID());
    }

}
