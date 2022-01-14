package com.gorent.api.mapper;

import com.gorent.api.dto.RentApplicationDTO;
import com.gorent.api.model.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class RentMapper {

    public Page<RentApplicationDTO> rentsToRentApplicationsDTO(Page<Rent> rents) {
        return rents.map(RentApplicationDTO::new);
    }

}
