package org.open.budget.costlocator.service;

import lombok.Builder;
import lombok.Getter;
import org.open.budget.costlocator.entity.Address;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

@Builder
@Getter
public class SearchCriteria implements Serializable {

    private Long city;

    private Long street;

    private Long address;

    private Integer from;

    private Integer to;

    private List<String> tenderIds = EMPTY_LIST;

    public SearchCriteria(Long city, Long street, Long address, Integer from, Integer to, List<String> tenderIds) {
        this.city = city;
        this.street = street;
        this.address = address;
        this.from = from;
        this.to = to;
        this.tenderIds = tenderIds;
    }
}
