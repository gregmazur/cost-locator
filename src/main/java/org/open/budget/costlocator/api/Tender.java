
package org.open.budget.costlocator.api;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "tender")
public class Tender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long entityId;

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
    private String description;
    @SerializedName("title")
    @Expose
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
    @SerializedName("id")
    @Expose
    private String prozzorroId;
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
    @ManyToOne
    private ProcuringEntity tenderIssuer;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("tenderID")
    @Expose
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

    public Tender(){}

    private Tender(Builder builder) {
        setEntityId(builder.id);
        setProcurementMethod(builder.procurementMethod);
        setStatus(builder.status);
        setTenderPeriod(builder.tenderPeriod);
        setDocuments(builder.documents);
        setNumberOfBids(builder.numberOfBids);
        setDescription(builder.description);
        setTitle(builder.title);
        setMinimalStep(builder.minimalStep);
        setItem(builder.item);
        setProzzorroId(builder.prozzorroId);
        setProcurementMethodType(builder.procurementMethodType);
        setValue(builder.value);
        setSubmissionMethod(builder.submissionMethod);
        setTenderIssuer(builder.procuringEntity);
        setOwner(builder.owner);
        setTenderID(builder.tenderID);
        setEnquiryPeriod(builder.enquiryPeriod);
        setDate(builder.date);
        setGuarantee(builder.guarantee);
        setDateModified(builder.dateModified);
        setAwardCriteria(builder.awardCriteria);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Tender copy) {
        Builder builder = new Builder();
        builder.id = copy.getEntityId();
        builder.procurementMethod = copy.getProcurementMethod();
        builder.status = copy.getStatus();
        builder.tenderPeriod = copy.getTenderPeriod();
        builder.documents = copy.getDocuments();
        builder.numberOfBids = copy.getNumberOfBids();
        builder.description = copy.getDescription();
        builder.title = copy.getTitle();
        builder.minimalStep = copy.getMinimalStep();
        builder.item = copy.getItem();
        builder.prozzorroId = copy.getProzzorroId();
        builder.procurementMethodType = copy.getProcurementMethodType();
        builder.value = copy.getValue();
        builder.submissionMethod = copy.getSubmissionMethod();
        builder.procuringEntity = copy.getTenderIssuer();
        builder.owner = copy.getOwner();
        builder.tenderID = copy.getTenderID();
        builder.enquiryPeriod = copy.getEnquiryPeriod();
        builder.date = copy.getDate();
        builder.guarantee = copy.getGuarantee();
        builder.dateModified = copy.getDateModified();
        builder.awardCriteria = copy.getAwardCriteria();
        return builder;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getProcurementMethod() {
        return procurementMethod;
    }

    public String getStatus() {
        return status;
    }

    public TenderPeriod getTenderPeriod() {
        return tenderPeriod;
    }

    @OneToMany
    public List<Document> getDocuments() {
        return documents;
    }

    public Long getNumberOfBids() {
        return numberOfBids;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public MinimalStep getMinimalStep() {
        return minimalStep;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem() {
        return item;
    }

    public String getProzzorroId() {
        return prozzorroId;
    }

    public String getProcurementMethodType() {
        return procurementMethodType;
    }

    public Value getValue() {
        return value;
    }

    public String getSubmissionMethod() {
        return submissionMethod;
    }

    public ProcuringEntity getTenderIssuer() {
        return tenderIssuer;
    }

    public String getOwner() {
        return owner;
    }

    public String getTenderID() {
        return tenderID;
    }

    public EnquiryPeriod getEnquiryPeriod() {
        return enquiryPeriod;
    }

    public Date getDate() {
        return date;
    }

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public String getAwardCriteria() {
        return awardCriteria;
    }

    public void setAwardCriteria(String awardCriteria) {
        this.awardCriteria = awardCriteria;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public void setProcurementMethod(String procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTenderPeriod(TenderPeriod tenderPeriod) {
        this.tenderPeriod = tenderPeriod;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setNumberOfBids(Long numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMinimalStep(MinimalStep minimalStep) {
        this.minimalStep = minimalStep;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public void setProzzorroId(String prozzorroId) {
        this.prozzorroId = prozzorroId;
    }

    public void setProcurementMethodType(String procurementMethodType) {
        this.procurementMethodType = procurementMethodType;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setSubmissionMethod(String submissionMethod) {
        this.submissionMethod = submissionMethod;
    }

    public void setTenderIssuer(ProcuringEntity tenderIssuer) {
        this.tenderIssuer = tenderIssuer;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTenderID(String tenderID) {
        this.tenderID = tenderID;
    }

    public void setEnquiryPeriod(EnquiryPeriod enquiryPeriod) {
        this.enquiryPeriod = enquiryPeriod;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tender)) return false;
        Tender tender = (Tender) o;
        return getEntityId().equals(tender.getEntityId()) &&
                getProzzorroId().equals(tender.getProzzorroId()) &&
                getTenderID().equals(tender.getTenderID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntityId(), getProzzorroId(), getTenderID());
    }

    public static final class Builder {
        private Long id;
        private String procurementMethod;
        private String status;
        private TenderPeriod tenderPeriod;
        private List<Document> documents;
        private Long numberOfBids;
        private String description;
        private String title;
        private MinimalStep minimalStep;
        private Item item;
        private String prozzorroId;
        private String procurementMethodType;
        private Value value;
        private String submissionMethod;
        private ProcuringEntity procuringEntity;
        private String owner;
        private String tenderID;
        private EnquiryPeriod enquiryPeriod;
        private Date date;
        private Guarantee guarantee;
        private Date dateModified;
        private String awardCriteria;

        private Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withProcurementMethod(String val) {
            procurementMethod = val;
            return this;
        }

        public Builder withStatus(String val) {
            status = val;
            return this;
        }

        public Builder withTenderPeriod(TenderPeriod val) {
            tenderPeriod = val;
            return this;
        }

        public Builder withDocuments(List<Document> val) {
            documents = val;
            return this;
        }

        public Builder withNumberOfBids(Long val) {
            numberOfBids = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withMinimalStep(MinimalStep val) {
            minimalStep = val;
            return this;
        }

        public Builder withItems(Item val) {
            item = val;
            return this;
        }

        public Builder withProzzorroId(String val) {
            prozzorroId = val;
            return this;
        }

        public Builder withProcurementMethodType(String val) {
            procurementMethodType = val;
            return this;
        }

        public Builder withValue(Value val) {
            value = val;
            return this;
        }

        public Builder withSubmissionMethod(String val) {
            submissionMethod = val;
            return this;
        }

        public Builder withProcuringEntity(ProcuringEntity val) {
            procuringEntity = val;
            return this;
        }

        public Builder withOwner(String val) {
            owner = val;
            return this;
        }

        public Builder withTenderID(String val) {
            tenderID = val;
            return this;
        }

        public Builder withEnquiryPeriod(EnquiryPeriod val) {
            enquiryPeriod = val;
            return this;
        }

        public Builder withDate(Date val) {
            date = val;
            return this;
        }

        public Builder withGuarantee(Guarantee val) {
            guarantee = val;
            return this;
        }

        public Builder withDateModified(Date val) {
            dateModified = val;
            return this;
        }

        public Builder withAwardCriteria(String val) {
            awardCriteria = val;
            return this;
        }

        public Tender build() {
            return new Tender(this);
        }
    }
}
