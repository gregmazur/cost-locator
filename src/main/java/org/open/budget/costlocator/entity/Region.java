package org.open.budget.costlocator.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "region", indexes = {@Index(name = "REGION_NAME", unique = true, columnList = "name")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(length = 20)
    private String name;

    @Column(length = 60, name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "region", fetch = FetchType.EAGER)
    private Set<District> districts;

    public Optional<District> getDistrict(District district){
        if (districts == null)
            return Optional.empty();
        return districts.stream().filter(c -> c.equals(district)).findFirst();
    }

    public void addDistrict(District district){
        if (districts == null){
            districts = new HashSet<>();
        }
        districts.add(district);
    }
}
