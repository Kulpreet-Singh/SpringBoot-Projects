package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.BookedSeatDao;
import com.kulpreet.bookmyticket.dao.SeatDao;
import com.kulpreet.bookmyticket.dao.UserDao;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.BookedSeat;
import com.kulpreet.bookmyticket.model.Seat;
import com.kulpreet.bookmyticket.model.Show;
import com.kulpreet.bookmyticket.service.SeatService;
import com.kulpreet.bookmyticket.service.ShowService;
import com.kulpreet.bookmyticket.strategy.SeatLockStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_USER;
import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.SHOW_ALREADY_STARTED;

@Slf4j
@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatDao seatDao;

    @Autowired
    private SeatLockStrategy seatLockStrategy;

    @Autowired
    private BookedSeatDao bookedSeatDao;

    @Autowired
    private ShowService showService;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Seat> getSeats(boolean running, Long showId, Long userId) {
        validateShow(running, showId, userId);
        List<Seat> allSeats = seatDao.findByShowId(showId);
        List<BookedSeat> bookedSeats = bookedSeatDao.findByShowId(showId);
        allSeats.stream()
                .filter(seat -> (bookedSeats.stream().anyMatch(bookedSeat -> bookedSeat.getSeatId().equals(seat.getId()))
                        || seatLockStrategy.isSeatLocked(showId, seat.getId(), userId.toString())))
                .forEach(seat -> seat.setBooked(true));
        return allSeats;
    }

    private void validateShow(boolean running, Long showId, Long userId) {
        Show show = showService.findById(showId);
        if (running && show.getShowTime().isBefore(LocalDateTime.now())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String showTime = formatter.format(Timestamp.valueOf(show.getShowTime()));
            log.error("Show has already started at: {}", showTime);
            throw new ValidationException(SHOW_ALREADY_STARTED.getCode(), SHOW_ALREADY_STARTED.getMessage() + showTime);
        }
        if(userDao.countById(userId) == 0) {
            log.error("No user found with id: {}", userId);
            throw new ValidationException(NO_SUCH_USER.getCode(), NO_SUCH_USER.getMessage() + userId);
        }
    }

    @Override
    public void save(Seat seat) {
        seatDao.save(seat);
    }
}

