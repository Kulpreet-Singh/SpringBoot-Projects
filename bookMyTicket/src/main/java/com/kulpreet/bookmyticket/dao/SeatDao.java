package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.Seat;

import java.util.List;

public interface SeatDao {
    Long save(Seat seat);

    List<Seat> findByShowId(Long showId);
}
