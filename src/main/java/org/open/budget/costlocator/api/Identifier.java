
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@EqualsAndHashCode
public class Identifier implements Serializable {

    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("legalName")
    @Expose
    private String legalName = "";

    public Identifier(String scheme, String prozzorroId, String legalName) {
        this.scheme = scheme;
        this.id = prozzorroId;
        this.legalName = legalName;
    }

    public Identifier() {
    }
}
