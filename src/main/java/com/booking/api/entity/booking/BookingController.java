package com.booking.api.entity.booking;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.booking.api.dto.BookingCreateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Booking", description = "Booking Endpoints")
@RequestMapping("/bookings")
@RestController
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingCreateDTO bookingCreateDTO) {

        log.info("create={}", bookingCreateDTO);

        Booking booking = bookingService.create(bookingCreateDTO);

        return new ResponseEntity<>(booking, OK);
    }
}
