package org.open.budget.costlocator.dto;

import lombok.Builder;

@Builder
public class RegionDTO {
    private Long id;
    private String name;

    public RegionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
