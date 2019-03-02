
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.open.budget.costlocator.entity.Tender;

public class TenderWrapper {

    @SerializedName("data")
    @Expose
    private Tender tender;

    public Tender getTender() {
        return tender;
    }

}
