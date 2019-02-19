
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "address")
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
    private String streetAddress;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("locality")
    @Expose
    private String locality;

    public Long getId() {
        return id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getRegion() {
        return region;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLocality() {
        return locality;
    }

}
