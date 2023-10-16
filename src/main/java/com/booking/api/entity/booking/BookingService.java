package com.booking.api.entity.booking;

import com.booking.api.dto.BookingCancelDTO;
import com.booking.api.dto.BookingCreateDTO;

import com.booking.api.dto.BookingUpdateDTO;
import jakarta.validation.Valid;

public interface BookingService {

    Booking create(BookingCreateDTO bookingCreateDTO);

    Booking update(BookingUpdateDTO bookingUpdateDTO);

    Booking cancel(BookingCancelDTO bookingCancelDTO);
}
