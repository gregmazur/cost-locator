
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

@Embeddable
public class Item {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("classification")
    @Expose
    @ManyToOne
    private Classification classification;
    @SerializedName("deliveryLocation")
    @Expose
    @ManyToOne
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
    private String id;
    @SerializedName("unit")
    @Expose
    @Embedded
    private Unit unit;
    @SerializedName("quantity")
    @Expose
    private Long quantity;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public DeliveryLocation getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(DeliveryLocation deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(DeliveryDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
