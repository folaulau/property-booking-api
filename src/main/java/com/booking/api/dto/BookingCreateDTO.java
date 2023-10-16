package com.booking.api.dto;

import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BookingCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String            guestName;

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
