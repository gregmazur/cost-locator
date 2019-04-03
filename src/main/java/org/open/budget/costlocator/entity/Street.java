package org.open.budget.costlocator.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "street", indexes = {@Index(unique = true, columnList = "fk_city,name,full_name,p_index"),
        @Index(unique = true, columnList = "p_index")})
@Getter
@Builder
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 60)
    private String name;
    @Column(length = 60, name = "full_name")
    private String fullName;
    @Column(length = 6, name = "p_index")
    private String index;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_city")
    private City city;
    @OneToMany(mappedBy = "street", fetch = FetchType.LAZY)
    private List<Address> addresses;

}
