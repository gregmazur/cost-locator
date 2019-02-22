
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@Getter
public class Identifier implements Serializable {

    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("legalName")
    @Expose
    private String legalName;

    public Identifier(String scheme, String prozzorroId, String legalName) {
        this.scheme = scheme;
        this.id = prozzorroId;
        this.legalName = legalName;
    }

    public Identifier() {
    }
}
