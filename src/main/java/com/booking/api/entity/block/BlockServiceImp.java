package com.booking.api.entity.block;

import com.booking.api.dto.BlockCancelDTO;
import com.booking.api.dto.BlockCreateDTO;
import com.booking.api.dto.BlockUpdateDTO;
import com.booking.api.entity.booking.Booking;
import com.booking.api.entity.booking.BookingRepository;
import com.booking.api.entity.booking.Status;
import com.booking.api.entity.property.Property;
import com.booking.api.entity.property.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class BlockServiceImp implements BlockService {

    @Autowired
    private BlockRepository   blockRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Block create(BlockCreateDTO blockCreateDTO) {

        Property property = propertyRepository.findById(blockCreateDTO.getPropertyId()).orElseThrow(()-> new RuntimeException("Property not found"));

        Block block = Block.builder()
                .startDate(blockCreateDTO.getStartDate())
                .endDate(blockCreateDTO.getEndDate())
                .purpose(blockCreateDTO.getReason())
                .property(property)
                .build();

        if(isBlockOverlapWithBooking(block)){
            throw new RuntimeException("Block is overlapping with booking");
        }

        block.setStatus(Status.BOOKED);

        return blockRepository.saveAndFlush(block);
    }

    @Override
    public Block update(BlockUpdateDTO blockUpdateDTO) {

       Block block = blockRepository.findByUuid(blockUpdateDTO.getUuid()).orElseThrow(() -> new RuntimeException("Block not found by uuid"));

       block.setStartDate(blockUpdateDTO.getStartDate());
       block.setEndDate(blockUpdateDTO.getEndDate());

        if(isBlockOverlapWithBooking(block)){
            throw new RuntimeException("Block is overlapping with booking");
        }

        return blockRepository.saveAndFlush(block);
    }

    @Override
    public Block cancel(BlockCancelDTO blockCancelDTO) {

        Block block = blockRepository.findByUuid(blockCancelDTO.getUuid()).orElseThrow(() -> new RuntimeException("Block not found"));

        block.setStatus(Status.CANCELLED);
        block.setCancellationReason(blockCancelDTO.getReason());

        return blockRepository.saveAndFlush(block);
    }

    private boolean isBlockOverlapWithBooking(Block block) {

        List<Booking> bookings =  bookingRepository.getBookingsByDates(block.getProperty().getId(),Status.BOOKED, block.getStartDate(), block.getEndDate());

        log.info("bookings={}",bookings);

        return !bookings.isEmpty();
    }
}
