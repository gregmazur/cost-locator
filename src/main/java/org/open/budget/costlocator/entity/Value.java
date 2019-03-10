
package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter
public class Value {

    private String currency;
    private Long amount;
    private Boolean valueAddedTaxIncluded;

    public Value(String currency, Long amount, Boolean valueAddedTaxIncluded) {
        this.currency = currency;
        this.amount = amount;
        this.valueAddedTaxIncluded = valueAddedTaxIncluded;
    }

    public Value() {
    }
}
