
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tender_issuer")
@Builder
@Getter
public class TenderIssuer {

    @SerializedName("contactPoint")
    @Expose
    @Transient
    private ContactPoint contactPoint;
    @SerializedName("identifier")
    @Expose
    @EmbeddedId
    private Identifier identifier;
    @SerializedName("name")
    @Expose
    @Column(columnDefinition = "text")
    private String name;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("address")
    @Expose
    @ManyToOne
    private Address address;
    @OneToMany
    private List<Tender> tenders;

    public TenderIssuer(ContactPoint contactPoint, Identifier identifier, String name, String kind, Address address,
                        List<Tender> tenders) {
        this.contactPoint = contactPoint;
        this.identifier = identifier;
        this.name = name;
        this.kind = kind;
        this.address = address;
        this.tenders = tenders;
    }

    public TenderIssuer() {
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
