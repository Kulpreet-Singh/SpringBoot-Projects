package com.kulpreet.bookmyticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class CreateShowDto {
    @NotNull
    private Long hallId;
    @NotNull
    private Long movieId;
    @NotBlank
    private String showTime;
}
