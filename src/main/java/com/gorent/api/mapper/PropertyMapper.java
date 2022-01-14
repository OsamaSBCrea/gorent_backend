package com.gorent.api.mapper;

import com.gorent.api.dto.PropertyDTO;
import com.gorent.api.model.Property;
import org.springframework.stereotype.Service;

@Service
public class PropertyMapper {

    public Property propertyDtoToProperty(PropertyDTO propertyDTO) {
        return new Property(propertyDTO);
    }

}
