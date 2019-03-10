
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import org.open.budget.costlocator.entity.Unit;

@Builder
@Getter
public class ItemAPI {

    @SerializedName("description")
    @Expose
    private String itemDescription;
    @SerializedName("classification")
    @Expose
    private ClassificationAPI classification;
    @SerializedName("deliveryLocation")
    @Expose
    private DeliveryLocationApi deliveryLocation;
    @SerializedName("deliveryAddress")
    @Expose
    private AddressAPI deliveryAddress;
    @SerializedName("deliveryDate")
    @Expose
    private DeliveryDateAPI deliveryDateAPI;
    @SerializedName("id")
    @Expose
    private String itemId;
    @SerializedName("unit")
    @Expose
    private UnitAPI unit;
    @SerializedName("quantity")
    @Expose
    private Long quantity;

    public ItemAPI(String itemDescription, ClassificationAPI classification, DeliveryLocationApi deliveryLocation,
                   AddressAPI deliveryAddress, DeliveryDateAPI deliveryDateAPI, String itemId, UnitAPI unit, Long quantity) {
        this.itemDescription = itemDescription;
        this.classification = classification;
        this.deliveryLocation = deliveryLocation;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDateAPI = deliveryDateAPI;
        this.itemId = itemId;
        this.unit = unit;
        this.quantity = quantity;
    }

    public ItemAPI() {
    }
}
