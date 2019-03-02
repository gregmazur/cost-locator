package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "street", indexes = {@Index(unique = true, columnList = "fk_city,name,p_index"),
        @Index(columnList = "name"), @Index(columnList = "p_index")})
@Getter
@Builder
@EqualsAndHashCode(exclude = {"id", "addresses"})
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 60)
    private String name;
    @Column(length = 6, name = "p_index")
    private String index;
    @ManyToOne
    @JoinColumn(name = "fk_city")
    private City city;
    @OneToMany(mappedBy = "street")
    private List<Address> addresses;

    public Street(Long id, String name, String index, City city, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.city = city;
        this.addresses = addresses;
    }

    public Street() {
    }
}
