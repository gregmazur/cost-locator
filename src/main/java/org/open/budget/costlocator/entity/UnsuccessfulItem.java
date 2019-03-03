package org.open.budget.costlocator.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tender_not_saved")
@Getter
public class UnsuccessfulItem {
    @Id
    String id;

    public UnsuccessfulItem(String id) {
        this.id = id;
    }

    public UnsuccessfulItem() {
    }
}
