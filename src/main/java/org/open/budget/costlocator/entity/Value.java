
package org.open.budget.costlocator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Embeddable
public class Value {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("amount")
    @Expose
    private Long amount;
    @SerializedName("valueAddedTaxIncluded")
    @Expose
    private Boolean valueAddedTaxIncluded;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Boolean getValueAddedTaxIncluded() {
        return valueAddedTaxIncluded;
    }

    public void setValueAddedTaxIncluded(Boolean valueAddedTaxIncluded) {
        this.valueAddedTaxIncluded = valueAddedTaxIncluded;
    }

}
