package org.open.budget.costlocator.dto;

import lombok.Builder;

@Builder
public class CityDTO {
    private Long id;
    private String name;
    private Long regionId;

    public CityDTO(Long id, String name, Long regionId) {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
    }
}
