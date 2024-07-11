package com.kulpreet.bookmyticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMovieDto {
    @NotBlank
    private String title;
    @NotNull
    private Integer duration;// in minutes
    @NotBlank
    private String language;
    @NotBlank
    private String genre;
    @NotBlank
    private String description;
    @NotBlank
    private String releaseDate;
}
