package com.gorent.api.mapper;

import com.gorent.api.dto.UserDTO;
import com.gorent.api.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getAgency() != null ? user.getAgency().getName() : user.getTenant().getFullName(),
                user.getRole(),
                user.getAgency() != null ? user.getAgency().getId() : null,
                user.getTenant() != null ? user.getTenant().getId() : null,
                user.getAgency() != null ? user.getAgency().getCountry().getId() : user.getTenant().getCountry().getId()
        );
    }
}
