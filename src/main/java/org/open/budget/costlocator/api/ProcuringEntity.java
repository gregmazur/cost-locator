
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "tender_issuer")
public class ProcuringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @SerializedName("contactPoint")
    @Expose
    @Transient
    private ContactPoint contactPoint;
    @SerializedName("identifier")
    @Expose
    @Embedded
    private Identifier identifier;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("address")
    @Expose
    @ManyToOne
    private Address address;

    public Long getId() {
        return id;
    }

    public ContactPoint getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(ContactPoint contactPoint) {
        this.contactPoint = contactPoint;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
