
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Embeddable
@Builder
@Getter
public class Item {

    @SerializedName("description")
    @Expose
    @Column(length = 5000)
    private String itemDescription;
    @SerializedName("classification")
    @Expose
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Classification classification;
    @SerializedName("deliveryLocation")
    @Expose
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DeliveryLocation deliveryLocation;
    @SerializedName("deliveryAddress")
    @Expose
    @ManyToOne
    private Address deliveryAddress;
    @SerializedName("deliveryDate")
    @Expose
    @Embedded
    private DeliveryDate deliveryDate;
    @SerializedName("id")
    @Expose
    private String itemId;
    @SerializedName("unit")
    @Expose
    @Embedded
    private Unit unit;
    @SerializedName("quantity")
    @Expose
    private Long quantity;

    public Item(String itemDescription, Classification classification, DeliveryLocation deliveryLocation,
                Address deliveryAddress, DeliveryDate deliveryDate, String itemId, Unit unit, Long quantity) {
        this.itemDescription = itemDescription;
        this.classification = classification;
        this.deliveryLocation = deliveryLocation;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.itemId = itemId;
        this.unit = unit;
        this.quantity = quantity;
    }

    public Item() {
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}
