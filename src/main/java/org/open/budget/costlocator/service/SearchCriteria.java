package org.open.budget.costlocator.service;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchCriteria {

    private List<String> tenderIds;

    public SearchCriteria(List<String> tenderIds) {
        this.tenderIds = tenderIds;
    }
}
