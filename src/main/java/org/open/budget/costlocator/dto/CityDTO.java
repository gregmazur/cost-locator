package org.open.budget.costlocator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    private String district;
}
