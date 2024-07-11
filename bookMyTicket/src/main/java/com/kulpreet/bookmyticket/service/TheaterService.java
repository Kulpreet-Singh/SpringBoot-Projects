package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.model.Theater;

import java.util.List;

public interface TheaterService {
    Long onboardTheater(Theater theater);

    List<Theater> findAll();

    List<Theater> findByCityId(Long cityId);
}
