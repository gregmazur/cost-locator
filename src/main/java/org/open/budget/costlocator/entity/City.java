package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "city", indexes = {@Index(unique = true, columnList = "fk_region,name")})
@Getter
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 60)
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_region")
    private Region region;
    @OneToMany(mappedBy = "city")
    private List<Street> streets;
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;

    public City(Long id, String name, Region region, List<Street> streets, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.streets = streets;
        this.addresses = addresses;
    }

    public City() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return getName().equals(city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
