package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ComplaintPeriod {

    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("endDate")
    @Expose
    public String endDate;

    public ComplaintPeriod(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
