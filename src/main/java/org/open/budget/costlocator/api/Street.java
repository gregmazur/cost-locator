package org.open.budget.costlocator.api;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "street", indexes = {@Index(name = "RCN_INDEX", unique = true, columnList = "region,city,name")})
@Getter
@Builder
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 60)
    private String name;
    @Column(length = 20)
    private String region;
    @Column(length = 60)
    private String city;
    @OneToMany(mappedBy = "street")
    private List<Address> addresses;

    public Street(Long id, String name, String region, String city, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.city = city;
        this.addresses = addresses;
    }

    public Street() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Street)) return false;
        Street street = (Street) o;
        return Objects.equals(getName(), street.getName()) &&
                Objects.equals(getRegion(), street.getRegion()) &&
                Objects.equals(getCity(), street.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRegion(), getCity());
    }
}
