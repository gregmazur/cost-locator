package org.open.budget.costlocator.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tender_not_saved")
@Getter
public class UnsuccessfulItem implements Item{
    @Id
    String id;

    boolean active;

    public UnsuccessfulItem(String id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public UnsuccessfulItem() {
    }
}
