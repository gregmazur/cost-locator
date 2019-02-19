
package org.open.budget.costlocator.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TenderListWrapper {

    @SerializedName("next_page")
    @Expose
    private NextPage nextPage;
    @SerializedName("data")
    @Expose
    private List<TenderListItem> data = null;
    @SerializedName("prev_page")
    @Expose
    private PrevPage prevPage;

    public NextPage getNextPage() {
        return nextPage;
    }

    public List<TenderListItem> getTenderList() {
        return data;
    }

    public PrevPage getPrevPage() {
        return prevPage;
    }
}
