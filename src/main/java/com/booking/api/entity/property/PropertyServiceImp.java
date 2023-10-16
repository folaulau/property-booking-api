package com.booking.api.entity.property;

import com.booking.api.dto.PropertyCreateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PropertyServiceImp implements  PropertyService{

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Property create(PropertyCreateDTO propertyCreateDTO) {

        Property property = Property.builder()
                .address(propertyCreateDTO.getAddress())
                .build();

        return propertyRepository.saveAndFlush(property);
    }
}
