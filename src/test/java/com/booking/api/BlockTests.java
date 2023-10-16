package com.booking.api;

import com.booking.api.dto.BlockCancelDTO;
import com.booking.api.dto.BlockCreateDTO;
import com.booking.api.dto.BlockUpdateDTO;
import com.booking.api.dto.BookingCreateDTO;
import com.booking.api.entity.block.Block;
import com.booking.api.entity.block.BlockService;
import com.booking.api.entity.booking.Booking;
import com.booking.api.entity.booking.BookingService;
import com.booking.api.entity.booking.Status;
import com.booking.api.entity.property.Property;
import com.booking.api.entity.property.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class BlockTests {

    @Autowired
    private BlockService blockService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property = null;

    @BeforeEach
    public void init(){
        property = propertyRepository.saveAndFlush(Property.builder()
                        .id(1L)
                .address("123 Don rd, 12344 TX")
                .build());
    }

    @Test
    void itShouldCreateBlock() {

        BlockCreateDTO blockCreateDTO = BlockCreateDTO.builder()
                .propertyId(property.getId())
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Block savedBlock = blockService.create(blockCreateDTO);
        
        log.info("savedBlock={}",savedBlock);
    }

    @Test
    void itShouldNotCreateBlockOnExistingBooking() {

        BookingCreateDTO bookingCreateDTO = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking = bookingService.create(bookingCreateDTO);

        log.info("savedBooking={}",savedBooking);

        BlockCreateDTO blockCreateDTO1 = BlockCreateDTO.builder()
                .propertyId(property.getId())
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 13))
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            blockService.create(blockCreateDTO1);
        });

        log.info("error message {}", thrown.getMessage());

        assertThat(thrown.getMessage()).isEqualTo("Block is overlapping with booking");
    }

    @Test
    void itShouldCreateBlockBetweenExistingBookings() {

        BookingCreateDTO bookingCreateDTO1 = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking1 = bookingService.create(bookingCreateDTO1);

        log.info("savedBooking1={}",savedBooking1);

        BookingCreateDTO bookingCreateDTO2 = BookingCreateDTO.builder().guestName("Peter")
                .startDate(LocalDate.of(2023, 10, 20))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 24)).build();

        Booking savedBooking2 = bookingService.create(bookingCreateDTO2);

        log.info("savedBooking={}",savedBooking2);

        BlockCreateDTO blockCreateDTO1 = BlockCreateDTO.builder()
                .propertyId(property.getId())
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 15))
                .endDate(LocalDate.of(2023, 10, 19)).build();

        Block block = blockService.create(blockCreateDTO1);

        log.info("block={}",block);

        assertThat(block).isNotNull();
    }

    @Test
    void itShouldNotCreateBlockBetweenExistingBookings() {

        BookingCreateDTO bookingCreateDTO1 = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking1 = bookingService.create(bookingCreateDTO1);

        log.info("savedBooking1={}",savedBooking1);

        BookingCreateDTO bookingCreateDTO2 = BookingCreateDTO.builder().guestName("Peter")
                .startDate(LocalDate.of(2023, 10, 20))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 24)).build();

        Booking savedBooking2 = bookingService.create(bookingCreateDTO2);

        log.info("savedBooking={}",savedBooking2);

        BlockCreateDTO blockCreateDTO1 = BlockCreateDTO.builder()
                .propertyId(property.getId())
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 15))
                .endDate(LocalDate.of(2023, 10, 20)).build();

        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            blockService.create(blockCreateDTO1);
        });

        log.info("error message {}", thrown.getMessage());

        assertThat(thrown.getMessage()).isEqualTo("Block is overlapping with booking");
    }

    @Test
    void itShouldUpdate() {

        BookingCreateDTO bookingCreateDTO1 = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking1 = bookingService.create(bookingCreateDTO1);

        log.info("savedBooking1={}",savedBooking1);

        BlockCreateDTO blockCreateDTO1 = BlockCreateDTO.builder()
                .propertyId(property.getId())
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 3))
                .endDate(LocalDate.of(2023, 10, 8)).build();

        Block block = blockService.create(blockCreateDTO1);

        BlockUpdateDTO blockUpdateDTO = BlockUpdateDTO.builder()
                .uuid(block.getUuid())
                .startDate(block.getStartDate())
                .endDate(LocalDate.of(2023, 10, 9))
                .build();

        block = blockService.update(blockUpdateDTO);

        assertThat(block).isNotNull();
    }

    @Test
    void itShouldNotUpdate() {

        BookingCreateDTO bookingCreateDTO1 = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking1 = bookingService.create(bookingCreateDTO1);

        log.info("savedBooking1={}",savedBooking1);

        BlockCreateDTO blockCreateDTO1 = BlockCreateDTO.builder()
                .propertyId(property.getId())
                .reason("Cleaning")
                .startDate(LocalDate.of(2023, 10, 3))
                .endDate(LocalDate.of(2023, 10, 8)).build();

        Block block = blockService.create(blockCreateDTO1);

        BlockUpdateDTO blockUpdateDTO = BlockUpdateDTO.builder()
                .uuid(block.getUuid())
                .startDate(block.getStartDate())
                .endDate(LocalDate.of(2023, 10, 14))
                .build();

        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            blockService.update(blockUpdateDTO);
        });

        log.info("error message {}", thrown.getMessage());

        assertThat(thrown.getMessage()).isEqualTo("Block is overlapping with booking");
    }

    @Test
    void itShouldCancelBlock() {

        BookingCreateDTO bookingCreateDTO1 = BookingCreateDTO.builder().guestName("John")
                .startDate(LocalDate.of(2023, 10, 10))
                .propertyId(property.getId())
                .endDate(LocalDate.of(2023, 10, 14)).build();

        Booking savedBooking1 = bookingService.create(bookingCreateDTO1);

        log.info("savedBooking1={}",savedBooking1);

        BlockCancelDTO blockCancelDTO = BlockCancelDTO.builder()
                .reason("No longer needed")
                .uuid(savedBooking1.getUuid())
                .build();

        Block savedBlock = blockService.cancel(blockCancelDTO);

        log.info("savedBlock={}",savedBlock);

        assertThat(savedBlock).isNotNull();
        assertThat(savedBlock.getStatus()).isEqualTo(Status.CANCELLED);
    }

}
