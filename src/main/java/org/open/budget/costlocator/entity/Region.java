package org.open.budget.costlocator.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region", indexes = {@Index(unique = true, columnList = "name")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 20, name = "full_name")
    private String fullName;
    @OneToMany(mappedBy = "region")
    private List<City> cities;
}
