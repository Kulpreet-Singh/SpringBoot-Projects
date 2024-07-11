package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.model.Movie;

import java.util.List;

public interface MovieService {
    Long save(Movie movie);

    List<Movie> findAll();

    Movie findById(Long id);

    void update(Movie movie);
}
