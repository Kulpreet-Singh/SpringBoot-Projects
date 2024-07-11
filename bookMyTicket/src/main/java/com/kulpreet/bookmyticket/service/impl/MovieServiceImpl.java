package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.MovieDao;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.Movie;
import com.kulpreet.bookmyticket.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_MOVIE;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public Long save(Movie movie) {
        if(movieDao.countByTitleAndLanguage(movie.getTitle(), movie.getLanguage()) > 0) {
            throw new ValidationException("Movie with title: " + movie.getTitle() + " and language: " + movie.getLanguage() + " already exists");
        }
        return movieDao.save(movie);
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public Movie findById(Long id) {
        return movieDao.findById(id);
    }

    @Override
    public void update(Movie movie) {
        if(movieDao.countById(movie.getId()) == 0) {
            throw new ValidationException(NO_SUCH_MOVIE.getCode(), NO_SUCH_MOVIE.getMessage() + movie.getId());
        }
        if(movieDao.countByTitleAndLanguageAndIdNot(movie.getTitle(), movie.getLanguage(), movie.getId()) > 0) {
            throw new ValidationException("Movie with title: " + movie.getTitle() + " and language: "
                    + movie.getLanguage() + " already exists");
        }
        int updatedRecords = movieDao.update(movie);
        log.info("Updated records: {}", updatedRecords);
    }
}


