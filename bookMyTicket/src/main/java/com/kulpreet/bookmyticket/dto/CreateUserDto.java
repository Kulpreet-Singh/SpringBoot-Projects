package com.kulpreet.bookmyticket.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String mobile;
    @NotBlank
    private String gender;
    @NotNull
    private Integer age;
}
