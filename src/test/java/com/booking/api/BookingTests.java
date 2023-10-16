package com.booking.api;

import java.time.LocalDate;

import com.booking.api.dto.BookingCancelDTO;
import com.booking.api.dto.BookingCreateDTO;
import com.booking.api.dto.BookingUpdateDTO;
import com.booking.api.entity.property.Property;
import com.booking.api.entity.property.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.booking.api.entity.booking.Booking;
import com.booking.api.entity.booking.BookingService;
import com.booking.api.entity.booking.Status;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class BookingTests {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property = null;

    @BeforeEach
    public void init(){
        property = propertyRepository.saveAndFlush(Property.builder()
                        .address("123 Don rd, 12344 TX")
                .build());
    }

    @Test
    void itShouldCreateBooking() {

        BookingCreateDTO bookingCreateDTO = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking = bookingService.create(bookingCreateDTO);
        
        log.info("savedBooking={}",savedBooking);
    }

    @Test
    void itShouldNotCreateBooking() {

        BookingCreateDTO bookingCreateDTO = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking = bookingService.create(bookingCreateDTO);

        log.info("savedBooking={}",savedBooking);

        BookingCreateDTO booking2CreateDTO = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 11))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 13)).build();

        assertThrows(RuntimeException.class, () -> {
            bookingService.create(booking2CreateDTO);
        });
    }

    @Test
    void itShouldUpdateBooking() {

        BookingCreateDTO bookingCreateDTO = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking = bookingService.create(bookingCreateDTO);

        log.info("savedBooking={}",savedBooking);

        BookingUpdateDTO bookingUpdateDTO = BookingUpdateDTO.builder()
                .uuid(savedBooking.getUuid())
                .startDate(savedBooking.getStartDate())
                .endDate(LocalDate.of(2023, 10, 16))
                .build();

        savedBooking = bookingService.update(bookingUpdateDTO);
    }

    @Test
    void itShouldCancelBooking() {

        BookingCreateDTO bookingCreateDTO = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking = bookingService.create(bookingCreateDTO);

        log.info("savedBooking={}",savedBooking);

        BookingCancelDTO bookingCancelDTO = BookingCancelDTO.builder()
                .reason("Not needed anymore")
                .uuid(savedBooking.getUuid())
                .build();

        savedBooking = bookingService.cancel(bookingCancelDTO);


    }
}
