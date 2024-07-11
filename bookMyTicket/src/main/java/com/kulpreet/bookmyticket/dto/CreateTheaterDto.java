package com.kulpreet.bookmyticket.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateTheaterDto {
    @NotNull
    private Long cityId;
    @NotBlank
    private String name;
}
