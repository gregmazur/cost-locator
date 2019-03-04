
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;

@Builder
@Getter
public class ClassificationAPI {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("description")
    @Expose
    private String description;

    public ClassificationAPI(String id, String scheme, String description) {
        this.id = id;
        this.scheme = scheme;
        this.description = description;
    }

    public ClassificationAPI() {
    }
}
