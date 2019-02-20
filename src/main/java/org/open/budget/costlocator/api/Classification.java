
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "classification")
public class Classification {

    @Id
    @SerializedName("id")
    @Expose
    private String prozzorroId;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("description")
    @Expose
    private String description;

    public String getScheme() {
        return scheme;
    }

    public String getDescription() {
        return description;
    }

    public String getProzzorroId() {
        return prozzorroId;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProzzorroId(String prozzorroId) {
        this.prozzorroId = prozzorroId;
    }
}
