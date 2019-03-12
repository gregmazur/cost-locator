package org.open.budget.costlocator.dto;

import lombok.Builder;

@Builder
public class AddressDTO {
    private Long id;
    private String houseNumber;
    private Long cityId;
    private Long streetId;

    public AddressDTO(Long id, String houseNumber, Long cityId, Long streetId) {
        this.id = id;
        this.houseNumber = houseNumber;
        this.cityId = cityId;
        this.streetId = streetId;
    }
}
