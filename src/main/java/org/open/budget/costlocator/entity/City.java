package org.open.budget.costlocator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "city", indexes = {@Index(unique = true, columnList = "fk_region,name")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 60)
    private String name;
    @Column(length = 60, name = "full_name")
    private String fullName;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_region")
    private Region region;
    @OneToMany(mappedBy = "city")
    private List<Street> streets;
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;

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
