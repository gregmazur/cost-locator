package org.open.budget.costlocator.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "city", indexes = {@Index(name = "CITY_REG_NAME",unique = true, columnList = "fk_district,name")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(length = 60)
    private String name;

    @Column(length = 100, name = "full_name")
    private String fullName;

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "fk_district",nullable = false)
    private District district;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private Set<Street> streets;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Address> addresses;

    public Optional<Street> getStreet(Street street) {
        if (streets == null)
            return Optional.empty();
        return streets.stream().filter(s -> street.equals(s)).findFirst();
    }

    public void addStreet(Street street) {
        if (streets == null) {
            streets = new HashSet<>();
        }
        streets.add(street);
    }
}
