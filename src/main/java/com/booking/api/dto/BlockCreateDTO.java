package com.booking.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BlockCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String            reason;

    @NotNull
    @Future
    private LocalDate         startDate;

    @NotNull
    @Future
    private LocalDate         endDate;

    @Min(value = 1)
    @NotNull
    private Long propertyId;
}
