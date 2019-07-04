package org.open.budget.costlocator.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "district", indexes = {@Index(name = "DISTRICT_REG_NAME", unique = true, columnList = "fk_region,name")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(length = 60)
    private String name;

    @Column(length = 60, name = "full_name")
    private String fullName;

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "fk_region", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "district", fetch = FetchType.EAGER)
    private Set<City> cities;

    public Optional<City> getCity(City city){
        if (cities == null)
            return Optional.empty();
//        if (city.getName().equals("зачатівка")){
//            System.out.println(city.getFullName());
//            City city1 = cities.stream().filter(c -> c.getId()==4253).findFirst().get();
//            System.out.println(city1.getName().equals(city.getName()));
//            System.out.println("db = " + city1.getName().getBytes() + " , new = " + city.getName());
//        }
        return cities.stream().filter(c -> c.equals(city)).findFirst();
    }

    public void addCity(City city){
        if (cities == null){
            cities = new HashSet<>();
        }
        cities.add(city);
    }
}
