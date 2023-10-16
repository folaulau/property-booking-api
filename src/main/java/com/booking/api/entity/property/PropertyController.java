package com.booking.api.entity.property;

import com.booking.api.dto.BlockCreateDTO;
import com.booking.api.dto.PropertyCreateDTO;
import com.booking.api.entity.block.Block;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Property", description = "Property Endpoints")
@RequestMapping("/properties")
@RestController
@Slf4j
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Property> create(@Valid @RequestBody PropertyCreateDTO propertyCreateDTO) {

        log.info("create={}", propertyCreateDTO);

        Property property = propertyService.create(propertyCreateDTO);

        return new ResponseEntity<>(property, OK);
    }
}
