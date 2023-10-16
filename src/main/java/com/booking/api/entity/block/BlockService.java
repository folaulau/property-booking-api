package com.booking.api.entity.block;

import com.booking.api.dto.BlockCancelDTO;
import com.booking.api.dto.BlockCreateDTO;
import com.booking.api.dto.BlockUpdateDTO;

public interface BlockService {

    Block create(BlockCreateDTO blockCreateDTO);


    Block update(BlockUpdateDTO blockUpdateDTO);


    Block cancel(BlockCancelDTO blockCancelDTO);
}
