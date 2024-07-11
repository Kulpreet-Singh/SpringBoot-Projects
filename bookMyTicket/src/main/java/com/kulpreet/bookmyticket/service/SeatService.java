package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.model.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getSeats(boolean running, Long showId, Long userId);

    void save(Seat seat);
}
