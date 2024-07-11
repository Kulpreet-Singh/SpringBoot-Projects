package com.kulpreet.bookmyticket.model;

import com.kulpreet.bookmyticket.dto.CreateMovieDto;
import com.kulpreet.bookmyticket.dto.EditMovieDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private int duration;// in minutes
    private String language;
    private String genre;
    private String description;
    private String releaseDate;

    public Movie(CreateMovieDto createMovieDto) {
        this.title = createMovieDto.getTitle();
        this.duration = createMovieDto.getDuration();
        this.language = createMovieDto.getLanguage();
        this.genre = createMovieDto.getGenre();
        this.description = createMovieDto.getDescription();
        this.releaseDate = createMovieDto.getReleaseDate();
    }

    public Movie(EditMovieDto editMovieDto) {
        this.id = editMovieDto.getId();
        this.title = editMovieDto.getTitle();
        this.duration = editMovieDto.getDuration();
        this.language = editMovieDto.getLanguage();
        this.genre = editMovieDto.getGenre();
        this.description = editMovieDto.getDescription();
        this.releaseDate = editMovieDto.getReleaseDate();
    }
}

