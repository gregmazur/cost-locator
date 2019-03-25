package org.open.budget.costlocator.service;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SearchCriteria implements Serializable {

    private Long city;

    private Long street;

    private Long address;

    private Integer page;

    private Integer size;

    private boolean isResultSizeNeeded;

    private List<String> tenderIds = EMPTY_LIST;
}
