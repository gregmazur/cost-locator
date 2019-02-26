package org.open.budget.costlocator.api;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "street", indexes = {@Index(name = "STREET_INDEX", unique = true, columnList = "region,city,name")})
@Getter
@Builder
public class Street {
    @Id
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
}
