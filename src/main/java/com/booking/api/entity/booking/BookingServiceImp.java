package com.booking.api.entity.booking;

import com.booking.api.dto.BookingCancelDTO;
import com.booking.api.dto.BookingUpdateDTO;
import com.booking.api.entity.block.Block;
import com.booking.api.entity.property.Property;
import com.booking.api.entity.property.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.api.dto.BookingCreateDTO;
import com.booking.api.entity.block.BlockRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BlockRepository   blockRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Booking create(BookingCreateDTO bookingCreateDTO) {

        Property property = propertyRepository.findById(bookingCreateDTO.getPropertyId()).orElseThrow(()-> new RuntimeException("Property not found"));

        Booking booking = new Booking();
        booking.setGuestName(bookingCreateDTO.getGuestName());
        booking.setStartDate(bookingCreateDTO.getStartDate());
        booking.setEndDate(bookingCreateDTO.getEndDate());
        booking.setStatus(Status.BOOKED);
        booking.setProperty(property);

        if(isBookingOverlap(booking)){
            throw new RuntimeException("Booking is overlapping");
        }

        return bookingRepository.saveAndFlush(booking);
    }

    @Override
    public Booking update(BookingUpdateDTO bookingUpdateDTO) {

        Booking booking = bookingRepository.findByUuid(bookingUpdateDTO.getUuid()).orElseThrow(() -> new RuntimeException("Booking not found by uuid"));

        booking.setStartDate(bookingUpdateDTO.getStartDate());
        booking.setEndDate(bookingUpdateDTO.getEndDate());

        if(isBookingOverlap(booking)){
            throw new RuntimeException("Booking is overlapping");
        }

        booking.setNumberOfGuests(bookingUpdateDTO.getNumberOfGuests());
        booking.setGuestName(bookingUpdateDTO.getGuestName());

        return bookingRepository.saveAndFlush(booking);
    }

    @Override
    public Booking cancel(BookingCancelDTO bookingCancelDTO) {
        Booking booking = bookingRepository.findByUuid(bookingCancelDTO.getUuid()).orElseThrow(() -> new RuntimeException("Booking not found by uuid"));
        booking.setStatus(Status.CANCELLED);
        booking.setCancellationReason(bookingCancelDTO.getReason());
        return bookingRepository.saveAndFlush(booking);
    }

    private boolean isBookingOverlap(Booking booking) {

        List<Block> blocks = blockRepository.getBlocksByDates(booking.getProperty().getId(), Status.BOOKED, booking.getStartDate(), booking.getEndDate());

        List<Booking> bookings =  bookingRepository.getBookingsByDates(booking.getProperty().getId(),Status.BOOKED, booking.getStartDate(), booking.getEndDate());

        if(booking.getId()!=null){
            // Remove self (important during updates)
            bookings.removeIf(b -> b.getId().equals(booking.getId()));
        }

        return !blocks.isEmpty() || !bookings.isEmpty();
    }

}
