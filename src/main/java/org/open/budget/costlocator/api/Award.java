package org.open.budget.costlocator.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Award {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;
    @SerializedName("complaintPeriod")
    @Expose
    private ComplaintPeriod complaintPeriod;
    @SerializedName("suppliers")
    @Expose
    private List<Supplier> suppliers = null;
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("value")
    @Expose
    private ValueAPI value;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private String id;

    public Award(String status, List<Document> documents, ComplaintPeriod complaintPeriod, List<Supplier> suppliers,
                 String bidId, ValueAPI value, String date, String id) {
        this.status = status;
        this.documents = documents;
        this.complaintPeriod = complaintPeriod;
        this.suppliers = suppliers;
        this.bidId = bidId;
        this.value = value;
        this.date = date;
        this.id = id;
    }
}
