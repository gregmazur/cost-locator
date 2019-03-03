package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address", indexes = {@Index(name = "STREET_INDEX", unique = true, columnList = "fk_city, fk_street, house_number")})
@Getter
@Builder
@EqualsAndHashCode(exclude = {"id", "tenders"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "house_number", length = 40)
    private String houseNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_city")
    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_street")
    private Street street;
    @ManyToMany
    private List<Tender> tenders;

    public Address(Long id, String houseNumber, City city, Street street, List<Tender> tenders) {
        this.id = id;
        this.houseNumber = houseNumber;
        this.city = city;
        this.street = street;
        this.tenders = tenders;
    }

    public Address() {
    }
}