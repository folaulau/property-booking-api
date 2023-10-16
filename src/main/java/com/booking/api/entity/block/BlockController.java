package com.booking.api.entity.block;

import com.booking.api.dto.*;
import com.booking.api.entity.booking.Booking;
import com.booking.api.entity.booking.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Block", description = "Block Endpoints")
@RequestMapping("/blocks")
@RestController
@Slf4j
public class BlockController {

    @Autowired
    private BlockService blockService;

    @PostMapping
    public ResponseEntity<Block> create(@Valid @RequestBody BlockCreateDTO blockCreateDTO) {

        log.info("create={}", blockCreateDTO);

        Block block = blockService.create(blockCreateDTO);

        return new ResponseEntity<>(block, OK);
    }

    @PutMapping
    public ResponseEntity<Block> update(@Valid @RequestBody BlockUpdateDTO blockUpdateDTO) {

        log.info("update={}", blockUpdateDTO);

        Block block = blockService.update(blockUpdateDTO);

        return new ResponseEntity<>(block, OK);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Block> cancel(@Valid @RequestBody BlockCancelDTO blockCancelDTO) {

        log.info("cancel={}", blockCancelDTO);

        Block block = blockService.cancel(blockCancelDTO);

        return new ResponseEntity<>(block, OK);
    }
}
