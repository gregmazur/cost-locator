
package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tender_issuer")
@Builder
@Getter
public class TenderIssuer {

    @Id
    @Column(length = 8)
    private String id;
    @Column(columnDefinition = "text")
    private String name;
    @Column(length = 10)
    private String kind;
    @ColumnDefault(value = "''")
    @Column(length = 32)
    private String scheme;
    @ColumnDefault(value = "''")
    @Column(length = 500)
    private String legalName = "";
    @OneToMany(mappedBy = "issuer")
    private List<Tender> tenders;

    public TenderIssuer(String id, String name, String kind, String scheme, String legalName, List<Tender> tenders) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.scheme = scheme;
        this.legalName = legalName;
        this.tenders = tenders;
    }

    public TenderIssuer() {
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setId(String id) {
        this.id = id;
    }
}
