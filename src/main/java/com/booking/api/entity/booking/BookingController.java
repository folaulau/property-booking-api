package com.booking.api.entity.booking;

import static org.springframework.http.HttpStatus.OK;

import com.booking.api.dto.BookingCancelDTO;
import com.booking.api.dto.BookingUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping
    public ResponseEntity<Booking> update(@Valid @RequestBody BookingUpdateDTO bookingUpdateDTO) {

        log.info("update={}", bookingUpdateDTO);

        Booking booking = bookingService.update(bookingUpdateDTO);

        return new ResponseEntity<>(booking, OK);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Booking> cancel(@Valid @RequestBody BookingCancelDTO bookingCancelDTO) {

        log.info("cancel={}", bookingCancelDTO);

        Booking booking = bookingService.cancel(bookingCancelDTO);

        return new ResponseEntity<>(booking, OK);
    }
}
