package org.open.budget.costlocator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tender_not_saved")
@Getter
@AllArgsConstructor
public class UnsuccessfulItem implements Item{
    @Id
    private String id;

    private boolean active;

    @Column(name = "full_text", columnDefinition = "text")
    private String fullText;

    public UnsuccessfulItem() {
    }
}
