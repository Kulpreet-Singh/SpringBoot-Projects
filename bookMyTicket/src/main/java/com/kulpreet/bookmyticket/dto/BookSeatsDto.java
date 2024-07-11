package com.kulpreet.bookmyticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BookSeatsDto {
    @NotNull
    private Long showId;
    @NotNull
    private List<Long> seatIds;
    @NotNull
    private Long userId;
}
