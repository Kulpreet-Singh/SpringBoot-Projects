package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.Movie;

import java.util.List;

public interface MovieDao {
    Long countById(Long id);

    Long countByTitleAndLanguage(String title, String language);

    Long countByTitleAndLanguageAndIdNot(String title, String language, Long id);

    Long save(Movie movie);

    List<Movie> findAll();

    Movie findById(Long id);

    int update(Movie movie);
}
