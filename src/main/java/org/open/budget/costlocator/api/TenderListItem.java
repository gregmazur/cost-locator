
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class TenderListItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dateModified")
    @Expose
    private Date dateModified;

    /**
     * No args constructor for use in serialization
     *
     */
    public TenderListItem() {
    }

    public String getId() {
        return id;
    }

    public Date getDateModified() {
        return dateModified;
    }
}
