package com.kulpreet.bookmyticket.controller;

import com.kulpreet.bookmyticket.dto.CreateMovieDto;
import com.kulpreet.bookmyticket.dto.EditMovieDto;
import com.kulpreet.bookmyticket.model.Movie;
import com.kulpreet.bookmyticket.service.MovieService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<String> register(@Valid @RequestBody CreateMovieDto movie) {
        log.info("Registering movie: {}", movie);
        Long movieId = movieService.save(new Movie(movie));
        log.info("Movie registered successfully with id: {}", movieId);
        return ResponseEntity.created(null).body("Movie registered successfully with id: " + movieId);
    }
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        log.info("Fetching all movies");
        return ResponseEntity.ok(movieService.findAll());
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long movieId) {
        log.info("Fetching movie with id: {}", movieId);
        return ResponseEntity.ok(movieService.findById(movieId));
    }
    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody EditMovieDto movie) {
        log.info("Updating movie: {}", movie);
        movieService.update(new Movie(movie));
        log.info("Movie updated successfully");
        return ResponseEntity.ok("Movie updated successfully");
    }
}

