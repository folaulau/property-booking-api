package com.booking.api.entity.booking;

import com.booking.api.dto.BookingCreateDTO;

import jakarta.validation.Valid;

public interface BookingService {

    Booking create(BookingCreateDTO bookingCreateDTO);

    Booking cancel(String uuid, String reason);
}
