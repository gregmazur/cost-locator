
package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "tender_detail")
@Builder
@Getter
public class TenderDetail {

    @Id
    @Column(length = 32)
    private String id;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "delivery_date")
    private DeliveryDate deliveryDate;
    @Embedded
    private Unit unit;
    private Long quantity;
    @Column(name = "procurement_method_type")
    private String procurementMethodType;
    private Value value;
    @Column(name = "submission_method")
    private String submissionMethod;
    @Column(name = "award_criteria")
    private String awardCriteria;


    public TenderDetail(String id, String description, DeliveryDate deliveryDate, Unit unit, Long quantity,
                        String procurementMethodType, Value value, String submissionMethod, String awardCriteria) {
        this.id = id;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.unit = unit;
        this.quantity = quantity;
        this.procurementMethodType = procurementMethodType;
        this.value = value;
        this.submissionMethod = submissionMethod;
        this.awardCriteria = awardCriteria;
    }

    public TenderDetail() {
    }
}
