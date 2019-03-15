
package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tender")
@Builder
@Getter
@EqualsAndHashCode(of = {"id"})
public class Tender {
    @Id
    @Column(length = 32)
    private String id;
    private String procurementMethod;
    private String status;
    private String owner;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String title;
    @Column(length = 32)
    private String tenderID;
    private Date date;
    private Date dateModified;

    @OneToOne(cascade = CascadeType.PERSIST)
    private TenderDetail tenderDetail;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Classification classification;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DeliveryLocation deliveryLocation;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private TenderIssuer issuer;
    @ManyToMany
    @JoinTable(name = "tender_addresses",
            joinColumns = @JoinColumn(name = "tender_id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id")
    )
    private Set<Address> addresses;

    public Tender() {
    }

    public Tender(String id, String procurementMethod, String status, String owner, String description, String title,
                  String tenderID, Date date, Date dateModified, TenderDetail tenderDetail, Classification classification,
                  DeliveryLocation deliveryLocation, TenderIssuer issuer, Set<Address> addresses) {
        this.id = id;
        this.procurementMethod = procurementMethod;
        this.status = status;
        this.owner = owner;
        this.description = description;
        this.title = title;
        this.tenderID = tenderID;
        this.date = date;
        this.dateModified = dateModified;
        this.tenderDetail = tenderDetail;
        this.classification = classification;
        this.deliveryLocation = deliveryLocation;
        this.issuer = issuer;
        this.addresses = addresses;
    }

    public void setIssuer(TenderIssuer issuer) {
        this.issuer = issuer;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public void setTenderDetail(TenderDetail tenderDetail) {
        this.tenderDetail = tenderDetail;
    }

}
