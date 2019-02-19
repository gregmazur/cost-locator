
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextPage {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("offset")
    @Expose
    private String offset;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NextPage() {
    }

    public String getPath() {
        return path;
    }

    public String getUri() {
        return uri;
    }

    public String getOffset() {
        return offset;
    }
}
