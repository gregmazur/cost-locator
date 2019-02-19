
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;

@Embeddable
public class Identifier {

    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("id")
    @Expose
    private String prozzorroId;
    @SerializedName("legalName")
    @Expose
    private String legalName;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getId() {
        return prozzorroId;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getProzzorroId() {
        return prozzorroId;
    }

    public void setProzzorroId(String prozzorroId) {
        this.prozzorroId = prozzorroId;
    }
}
