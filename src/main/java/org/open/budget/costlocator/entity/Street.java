package org.open.budget.costlocator.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "street", indexes = {
        @Index(name = "STREET_CITY_NAME_IND", unique = true, columnList = "fk_city,name,p_index")
        })
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(length = 60, name = "name")
    private String name;

    @Column(length = 100, name = "full_name")
    private String fullName;

    @EqualsAndHashCode.Include
    @Column(length = 6, name = "p_index")
    private String index;

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "fk_city",nullable = false)
    private City city;

    @OneToMany(mappedBy = "street", fetch = FetchType.LAZY)
    private Set<Address> addresses;
}
