
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MinimalStep {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("amount")
    @Expose
    private Float amount;
    @SerializedName("valueAddedTaxIncluded")
    @Expose
    private Boolean valueAddedTaxIncluded;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Boolean getValueAddedTaxIncluded() {
        return valueAddedTaxIncluded;
    }

    public void setValueAddedTaxIncluded(Boolean valueAddedTaxIncluded) {
        this.valueAddedTaxIncluded = valueAddedTaxIncluded;
    }

}
