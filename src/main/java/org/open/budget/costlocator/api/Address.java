
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address")
@Getter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("streetAddress")
    @Expose
    @Column(unique = true, length = 800)
    private String streetAddress;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("locality")
    @Expose
    private String locality;
    @OneToMany
    private List<TenderIssuer> tenderIssuers;
    @OneToMany
    private List<Tender> tenders;

    public Address(Long id, String postalCode, String countryName, String streetAddress, String region, String locality,
                   List<TenderIssuer> tenderIssuers, List<Tender> tenders) {
        this.id = id;
        this.postalCode = postalCode;
        this.countryName = countryName;
        this.streetAddress = streetAddress;
        this.region = region;
        this.locality = locality;
        this.tenderIssuers = tenderIssuers;
        this.tenders = tenders;
    }

    public Address() {
    }
}
