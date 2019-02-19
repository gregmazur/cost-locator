
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Guarantee {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("amount")
    @Expose
    private Long amount;

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

}
