
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classification")
@Builder
@Getter
public class Classification {

    @Id
    @Column(unique = true)
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("description")
    @Expose
    @Column(length = 1000)
    private String description;
    @OneToMany
    private List<Tender> tenders;

    public Classification(String id, String scheme, String description, List<Tender> tenders) {
        this.id = id;
        this.scheme = scheme;
        this.description = description;
        this.tenders = tenders;
    }

    public Classification() {
    }
}
