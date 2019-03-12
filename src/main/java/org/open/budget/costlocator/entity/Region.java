package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region", indexes = {@Index(unique = true, columnList = "name")})
@Getter
@Builder
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20)
    private String name;
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<City> cities;

    public Region(Long id, String name, List<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }

    public Region() {
    }
}
