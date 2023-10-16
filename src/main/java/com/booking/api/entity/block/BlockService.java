package com.booking.api.entity.block;

public interface BlockService {

    Block create(Block block);


    Block update(Block block);


    Block cancel(Long id, String reason);
}
