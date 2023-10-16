package com.booking.api.entity.property;

import com.booking.api.dto.PropertyCreateDTO;

public interface PropertyService {
    Property create(PropertyCreateDTO propertyCreateDTO);
}
