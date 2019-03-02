package org.open.budget.costlocator.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tenderDetail")
@Getter
public class UnsuccessfulItem {
    @Id
    String id;
}
