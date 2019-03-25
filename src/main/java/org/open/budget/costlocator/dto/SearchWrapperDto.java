package org.open.budget.costlocator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.open.budget.costlocator.service.SearchCriteria;

import java.awt.print.Pageable;

@Getter
@AllArgsConstructor
public class SearchWrapperDto {
    private SearchCriteria searchCriteria;
    private Pageable pageable;
}
