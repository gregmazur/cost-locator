package org.open.budget.costlocator.dto;

import lombok.Builder;

@Builder
public class StreetDTO {
    private Long id;
    private String name;
    private String index;
    private Long cityId;

    public StreetDTO(Long id, String name, String index, Long cityId) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.cityId = cityId;
    }
}
