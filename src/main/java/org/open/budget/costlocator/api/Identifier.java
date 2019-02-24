
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@Getter
public class Identifier implements Serializable {

    @SerializedName("scheme")
    @Expose
    @ColumnDefault(value = "''")
    @Column(length = 32)
    private String scheme;
    @SerializedName("id")
    @Expose
    @Column(length = 32)
    private String id;
    @SerializedName("legalName")
    @Expose
    @ColumnDefault(value = "''")
    @Column(length = 500)
    private String legalName = "";

    public Identifier(String scheme, String prozzorroId, String legalName) {
        this.scheme = scheme;
        this.id = prozzorroId;
        this.legalName = legalName;
    }

    public Identifier() {
    }
}
