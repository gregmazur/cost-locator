package org.open.budget.costlocator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
@AllArgsConstructor
public class TenderSearchResultDTO {
    private Collection<TenderDTO> tendersPortion;
    private Integer size;
}
