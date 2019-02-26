
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
public class AddressAPI {

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
    @Column(name = "region")
    private String region;
    @SerializedName("locality")
    @Expose
    private String locality;

    public AddressAPI(Long id, String postalCode, String countryName, String streetAddress, String region, String locality) {
        this.id = id;
        this.postalCode = postalCode;
        this.countryName = countryName;
        this.streetAddress = streetAddress;
        this.region = region;
        this.locality = locality;
    }

    public AddressAPI() {
    }
}
