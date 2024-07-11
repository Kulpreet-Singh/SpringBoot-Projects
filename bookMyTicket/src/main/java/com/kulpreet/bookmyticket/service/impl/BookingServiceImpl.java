package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.BookedSeatDao;
import com.kulpreet.bookmyticket.dao.BookingDao;
import com.kulpreet.bookmyticket.dao.ShowDao;
import com.kulpreet.bookmyticket.dao.UserDao;
import com.kulpreet.bookmyticket.dto.BookingInfo;
import com.kulpreet.bookmyticket.exception.SystemException;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.BookedSeat;
import com.kulpreet.bookmyticket.model.Booking;
import com.kulpreet.bookmyticket.model.Seat;
import com.kulpreet.bookmyticket.service.BookingService;
import com.kulpreet.bookmyticket.service.SeatService;
import com.kulpreet.bookmyticket.strategy.SeatLockStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.*;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private BookedSeatDao bookedSeatDao;
    @Autowired
    private SeatLockStrategy seatLockStrategy;
    @Autowired
    private SeatService seatService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShowDao showDao;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long bookSeats(Long userId, Long showId, List<Long> seatIds) {
        Long bookingId = null;
        validateSeats(userId, showId, seatIds);
        try {
            acquireLock(userId, showId, seatIds);
        } catch(ValidationException e) {
            releaseLock(userId, showId, seatIds);
            throw e;
        }
        // assume time to be taken for some step like payment
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new SystemException("Error while booking seats: "+ e.getMessage());
        }
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setBookingTime(LocalDateTime.now());
        bookingId = bookingDao.save(booking);
        for (Long seatId : seatIds) {
            BookedSeat bookedSeat = new BookedSeat();
            bookedSeat.setBookingId(bookingId);
            bookedSeat.setSeatId(seatId);
            bookedSeat.setShowId(showId);
            bookedSeatDao.save(bookedSeat);
        }
        releaseLock(userId, showId, seatIds);
        return bookingId;
    }

    private void acquireLock(Long userId, Long showId, List<Long> seatIds) {
        for (Long seatId : seatIds) {
            if (!seatLockStrategy.lockSeat(showId, seatId, userId.toString())) {
                log.error("User: {} cannot acquire lock for seat: {} of show: {}", userId, seatId, showId);
                throw new ValidationException(SEAT_ALREADY_BOOKED.getCode(), SEAT_ALREADY_BOOKED.getMessage() + seatId);
            } else {
                log.info("User: {} acquired lock for seat: {} of show: {}", userId, seatId, showId);
            }
        }
    }

    private void releaseLock(Long userId, Long showId, List<Long> seatIds) {
        for (Long seatId : seatIds) {
            if(seatLockStrategy.unlockSeat(showId, seatId, userId.toString())){
                log.info("User: {} released lock for seat: {} of show: {}", userId, seatId, showId);
            }
        }
    }

    private void validateSeats(Long userId, Long showId, List<Long> seatIds) {
        List<Seat> allSeats = seatService.getSeats(true, showId, userId);
        List<Long> validSeatIds = allSeats.stream().map(Seat::getId).toList();
        List<Long> invalidSeatIds = seatIds.stream().filter(seatId -> !validSeatIds.contains(seatId)).toList();
        if(!invalidSeatIds.isEmpty()) {
            log.error("Invalid seat ids: {}", invalidSeatIds);
            throw new ValidationException(INVALID_SEAT.getCode(), INVALID_SEAT.getMessage() + StringUtils.join(invalidSeatIds, ","));
        }

        List<Long> bookedSeats = allSeats.stream().filter(Seat::isBooked).map(Seat::getId).toList();
        List<Long> alreadyBookedSeats = seatIds.stream().filter(bookedSeats::contains).toList();
        if (!alreadyBookedSeats.isEmpty()) {
            log.error("Seats already booked: {}", alreadyBookedSeats);
            throw new ValidationException(SEAT_ALREADY_BOOKED.getCode(), SEAT_ALREADY_BOOKED.getMessage() + StringUtils.join(alreadyBookedSeats, ","));
        }
    }

    @Override
    public List<BookingInfo> getBookings(boolean upcoming, Long userId) {
        if(userDao.countById(userId) == 0) {
            log.error("No user found with id: {}", userId);
            throw new ValidationException(NO_SUCH_USER.getCode(), NO_SUCH_USER.getMessage() + userId);
        }
        List<BookingInfo> bookings = bookingDao.findByUserId(upcoming, userId);
        for(BookingInfo bookingInfo : bookings) {
            bookingInfo.setShowInfo(showDao.findShowInfo(false, null, null, null, bookingInfo.getShowId()).get(0));
        }
        return bookings;
    }
}
