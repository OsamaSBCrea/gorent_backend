package com.gorent.api.dto;

import com.gorent.api.model.Rent;
import com.gorent.api.model.enums.UserRole;

import java.util.List;

public class AgencyDTO extends UserDTO {

    private List<Rent> rents;

    public AgencyDTO(List<Rent> rents) {
        this.rents = rents;
    }

    public AgencyDTO(UserDTO userDTO) {
        super(userDTO.getId(), userDTO.getEmail(), userDTO.getName(), userDTO.getRole(), userDTO.getAgencyId(), userDTO.getTenantId(), userDTO.getCountryId());
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }
}
