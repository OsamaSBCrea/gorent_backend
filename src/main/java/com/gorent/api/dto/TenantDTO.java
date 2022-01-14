package com.gorent.api.dto;

import com.gorent.api.model.enums.RentStatus;
import com.gorent.api.model.enums.UserRole;

public class TenantDTO extends UserDTO {

    private Long currentRentedPropertyId;

    private RentStatus currentRentStatus;

    public TenantDTO(UserDTO userDTO) {
        super(userDTO.getId(), userDTO.getEmail(), userDTO.getName(), userDTO.getRole(), userDTO.getAgencyId(), userDTO.getTenantId(), userDTO.getCountryId());
    }

    public Long getCurrentRentedPropertyId() {
        return currentRentedPropertyId;
    }

    public void setCurrentRentedPropertyId(Long currentRentedPropertyId) {
        this.currentRentedPropertyId = currentRentedPropertyId;
    }

    public RentStatus getCurrentRentStatus() {
        return currentRentStatus;
    }

    public void setCurrentRentStatus(RentStatus currentRentStatus) {
        this.currentRentStatus = currentRentStatus;
    }
}
