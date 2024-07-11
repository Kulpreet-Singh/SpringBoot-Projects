package com.kulpreet.bookmyticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCityDto {
    @NotBlank
    private String name;
    @NotBlank
    private String state;
}
