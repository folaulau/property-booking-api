package com.booking.api.entity.block;

import com.booking.api.entity.booking.Booking;
import com.booking.api.entity.booking.BookingRepository;
import com.booking.api.entity.booking.Status;
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

    @Override
    public Block create(Block block) {
        return blockRepository.saveAndFlush(block);
    }

    @Override
    public Block update(Block block) {
        return blockRepository.saveAndFlush(block);
    }

    @Override
    public Block cancel(Long id, String reason) {

        Block block = blockRepository.findById(id).orElseThrow(() -> new RuntimeException("Block not found"));

        block.setStatus(Status.CANCELLED);

        return blockRepository.saveAndFlush(block);
    }
}
