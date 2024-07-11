package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.model.Hall;

import java.util.List;

public interface HallService {
    Long addHall(Hall hall);

    List<Hall> getHalls(Long theaterId);
}
