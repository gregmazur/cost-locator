package org.open.budget.costlocator.api;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item")
@Getter
public class UnsuccessfulItem {
    @Id
    String id;
}
