package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.*;
import com.kulpreet.bookmyticket.dto.ShowInfo;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.Show;
import com.kulpreet.bookmyticket.model.Theater;
import com.kulpreet.bookmyticket.service.MovieService;
import com.kulpreet.bookmyticket.service.ShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.*;

@Slf4j
@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private ShowDao showDao;
    @Autowired
    private MovieService movieService;
    @Autowired
    private HallDao hallDao;
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private TheaterDao theaterDao;

    @Override
    public Long hostShow(Show show) {
        validateShow(show);
        return showDao.save(show);
    }

    private void validateShow(Show show) {
        if(movieDao.countById(show.getMovieId()) == 0) {
            log.error("No movie found with id: {}", show.getMovieId());
            throw new ValidationException(NO_SUCH_MOVIE.getCode(), NO_SUCH_MOVIE.getMessage() + show.getMovieId());
        }
        if(hallDao.countById(show.getHallId()) == 0) {
            log.error("No hall found with id: {}", show.getHallId());
            throw new ValidationException(NO_SUCH_HALL.getCode(), NO_SUCH_HALL.getMessage() + show.getHallId());
        }
        if(show.getShowTime().isBefore(LocalDateTime.now())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String showTime = formatter.format(Timestamp.valueOf(show.getShowTime()));
            log.error("Cannot host show in the past: {}", showTime);
            throw new ValidationException(HOST_SHOW_PAST.getCode(), HOST_SHOW_PAST.getMessage() + showTime);
        }
        show.setEndTime(show.getShowTime().plusMinutes(movieService.findById(show.getMovieId()).getDuration()));
        if (isHallOccupied(show)) {
            log.error("Hall with id: {} is already occupied for the given time", show.getHallId());
            throw new ValidationException(HALL_OCCUPIED);
        }
    }

    @Override
    public Show findById(Long showId) {
        if(showDao.countById(showId) == 0) {
            log.error("No show found with id: {}", showId);
            throw new ValidationException(NO_SUCH_SHOW.getCode(), NO_SUCH_SHOW.getMessage() + showId);
        }
        return showDao.findById(showId);
    }

    @Override
    public List<ShowInfo> findShowInfo(boolean running, Long cityId, Long theaterId, Long movieId, Long showId) {
        if(cityId != null && cityDao.countById(cityId) == 0) {
            log.error("No city found with id: {}", cityId);
            throw new ValidationException(NO_SUCH_CITY.getCode(), NO_SUCH_CITY.getMessage() + cityId);
        }
        if(theaterId != null && theaterDao.countById(theaterId) == 0) {
            log.error("No theater found with id: {}", theaterId);
            throw new ValidationException(NO_SUCH_THEATER.getCode(), NO_SUCH_THEATER.getMessage() + theaterId);
        }
        if(movieId != null && movieDao.countById(movieId) == 0) {
            log.error("No movie found with id: {}", movieId);
            throw new ValidationException(NO_SUCH_MOVIE.getCode(), NO_SUCH_MOVIE.getMessage() + movieId);
        }
        if(showId != null && showDao.countById(showId) == 0) {
            log.error("No show found with id: {}", showId);
            throw new ValidationException(NO_SUCH_SHOW.getCode(), NO_SUCH_SHOW.getMessage() + showId);
        }
        return showDao.findShowInfo(running, cityId, theaterId, movieId, showId);
    }

    private boolean isHallOccupied(Show show) {
        Long count = showDao.countOverlappingShows(show.getHallId(), show.getShowTime(), show.getEndTime());
        return count > 0;
    }


}


