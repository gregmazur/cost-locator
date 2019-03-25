
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.open.budget.costlocator.entity.Unit;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Double quantity;
}
