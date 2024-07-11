package com.kulpreet.bookmyticket.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateHallDto {
    @NotNull
    private Long theaterId;
    @NotBlank
    private String name;
    @NotNull
    private Integer capacityRow;
    @NotNull
    private Integer capacityColumn;
}
