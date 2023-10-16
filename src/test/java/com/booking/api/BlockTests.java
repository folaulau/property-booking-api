package com.booking.api;

import com.booking.api.entity.block.Block;
import com.booking.api.entity.block.BlockService;
import com.booking.api.entity.booking.Booking;
import com.booking.api.entity.booking.BookingService;
import com.booking.api.entity.booking.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
class BlockTests {

    @Autowired
    private BlockService blockService;

    @Test
    void itShouldCreateBlock() {

        Block block = Block.builder()
                .status(Status.BOOKED)
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Block savedBlock = blockService.create(block);
        
        log.info("savedBlock={}",savedBlock);
    }

    @Test
    void itShouldUpdateBlock() {

        Block block = Block.builder()
                .status(Status.BOOKED)
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Block savedBlock = blockService.create(block);

        log.info("savedBlock={}",savedBlock);


        savedBlock.setEndDate(LocalDate.of(2023, 10, 20));


        savedBlock = blockService.update(savedBlock);

        log.info("savedBlock={}",savedBlock);
    }

    @Test
    void itShouldCancelBlock() {

        Block block = Block.builder()
                .status(Status.BOOKED)
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Block savedBlock = blockService.create(block);

        log.info("savedBlock={}",savedBlock);

        savedBlock = blockService.cancel(savedBlock.getId(),"no longer needed");

        log.info("savedBlock={}",savedBlock);
    }

}
