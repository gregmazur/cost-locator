
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TenderIssuerAPI {

    @SerializedName("contactPoint")
    @Expose
    private ContactPoint contactPoint;
    @SerializedName("identifier")
    @Expose
    private Identifier identifier;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("address")
    @Expose
    private AddressAPI address;

    public TenderIssuerAPI(ContactPoint contactPoint, Identifier identifier, String name, String kind, AddressAPI address) {
        this.contactPoint = contactPoint;
        this.identifier = identifier;
        this.name = name;
        this.kind = kind;
        this.address = address;
    }

    public TenderIssuerAPI() {
    }

    public void setAddress(AddressAPI address) {
        this.address = address;
    }
}
