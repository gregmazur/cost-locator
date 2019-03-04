
package org.open.budget.costlocator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class DeliveryDate {

    @SerializedName("startDate")
    @Expose
    private Date startDate;
    @SerializedName("endDate")
    @Expose
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
