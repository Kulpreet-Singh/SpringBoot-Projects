package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.BookedSeat;

import java.util.List;

public interface BookedSeatDao {
    List<BookedSeat> findByShowId(Long showId);

    List<BookedSeat> findByBookingId(Long bookingId);

    Long save(BookedSeat bookedSeat);
}
