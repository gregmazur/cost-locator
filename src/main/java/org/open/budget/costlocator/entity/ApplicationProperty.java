package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Temporary
@Entity
@Table(name = "app_prop")
@Getter
@Builder
public class ApplicationProperty {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "property")
    private String property;

    public ApplicationProperty() {
    }

    public ApplicationProperty(String id, String property) {
        this.id = id;
        this.property = property;
    }
}
