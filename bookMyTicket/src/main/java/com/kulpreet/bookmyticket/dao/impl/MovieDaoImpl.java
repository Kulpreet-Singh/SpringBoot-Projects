package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.MovieDao;
import com.kulpreet.bookmyticket.exception.SystemException;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_MOVIE;

@Slf4j
@Repository
public class MovieDaoImpl implements MovieDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Movie> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM movies", new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public Movie findById(Long id) {
        try{
            return namedParameterJdbcTemplate.queryForObject("SELECT * FROM movies WHERE id = :id", Map.of("id", id), new BeanPropertyRowMapper<>(Movie.class));
        } catch (EmptyResultDataAccessException e) {
            log.error("Movie with id {} not found", id);
            throw new ValidationException(NO_SUCH_MOVIE.getCode(), NO_SUCH_MOVIE.getMessage() + id);
        } catch (Exception e) {
            log.error("Error while fetching movie with id {}", id, e);
            throw new SystemException("Error while fetching movie");
        }
    }

    @Override
    public Long countById(Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM movies WHERE id = :id", Map.of("id", id), Long.class);
    }

    @Override
    public Long countByTitleAndLanguage(String title, String language) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM movies WHERE title = :title AND language = :language", Map.of("title", title, "language", language), Long.class);
    }

    @Override
    public Long countByTitleAndLanguageAndIdNot(String title, String language, Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM movies WHERE title = :title AND language = :language AND id != :id", Map.of("title", title, "language", language, "id", id), Long.class);
    }

    @Override
    public Long save(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", movie.getTitle())
                .addValue("duration", movie.getDuration())
                .addValue("language", movie.getLanguage())
                .addValue("genre", movie.getGenre())
                .addValue("description", movie.getDescription())
                .addValue("releaseDate", movie.getReleaseDate());
        namedParameterJdbcTemplate.update("INSERT INTO movies (title, duration, language, genre, description, release_date) VALUES (:title, :duration, :language, :genre, :description, :releaseDate)", namedParameters, keyHolder);
        movie.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Movie movie) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", movie.getTitle())
                .addValue("duration", movie.getDuration())
                .addValue("language", movie.getLanguage())
                .addValue("genre", movie.getGenre())
                .addValue("description", movie.getDescription())
                .addValue("releaseDate", movie.getReleaseDate())
                .addValue("id", movie.getId());
        return namedParameterJdbcTemplate.update("UPDATE movies SET title = :title, duration = :duration, language = :language, genre = :genre, description = :description, release_date = :releaseDate WHERE id = :id", namedParameters);
    }
}

