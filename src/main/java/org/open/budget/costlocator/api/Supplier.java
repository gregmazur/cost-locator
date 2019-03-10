package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Supplier {

    @SerializedName("contactPoint")
    @Expose
    private ContactPoint contactPoint;
    @SerializedName("identifier")
    @Expose
    private Identifier identifier;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private AddressAPI address;

    public Supplier(ContactPoint contactPoint, Identifier identifier, String name, AddressAPI address) {
        this.contactPoint = contactPoint;
        this.identifier = identifier;
        this.name = name;
        this.address = address;
    }
}
