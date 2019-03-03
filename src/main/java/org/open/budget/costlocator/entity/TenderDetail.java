
package org.open.budget.costlocator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import org.open.budget.costlocator.api.AddressAPI;
import org.open.budget.costlocator.api.DeliveryDate;

import javax.persistence.*;

@Embeddable
@Builder
@Getter
public class TenderDetail {

    @SerializedName("description")
    @Expose
    @Column(columnDefinition = "text")
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
    @Transient
    private AddressAPI deliveryAddress;
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

    public TenderDetail(String itemDescription, Classification classification, DeliveryLocation deliveryLocation,
                        AddressAPI deliveryAddress, DeliveryDate deliveryDate, String itemId, Unit unit, Long quantity) {
        this.itemDescription = itemDescription;
        this.classification = classification;
        this.deliveryLocation = deliveryLocation;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.itemId = itemId;
        this.unit = unit;
        this.quantity = quantity;
    }

    public TenderDetail() {
    }

    public void setDeliveryAddress(AddressAPI deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}