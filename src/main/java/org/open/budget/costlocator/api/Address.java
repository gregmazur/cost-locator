package org.open.budget.costlocator.api;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address", indexes = {@Index(name = "STREET_INDEX", unique = true, columnList = "fk_street, house_number, p_index"),
        @Index(name = "POST_INDEX", columnList = "p_index")})
@Getter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 6, name = "p_index")
    private String index;
    @Column(name = "house_number", length = 40)
    private String houseNumber;
    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(name = "stree")
    @JoinColumn(name = "fk_street")
    private Street street;
    @ManyToMany
    private List<Tender> tenders;

    public Address(Long id, String index, String houseNumber, Street street, List<Tender> tenders) {
        this.id = id;
        this.index = index;
        this.houseNumber = houseNumber;
        this.street = street;
        this.tenders = tenders;
    }

    public Address() {
    }
}
