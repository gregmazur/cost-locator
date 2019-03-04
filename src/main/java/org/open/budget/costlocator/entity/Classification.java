
package org.open.budget.costlocator.entity;

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
    @Column(unique = true, length = 32)
    private String id;
    private String scheme;
    @Column(length = 1000, columnDefinition = "text")
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
