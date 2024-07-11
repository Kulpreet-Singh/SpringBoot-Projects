package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.Hall;

import java.util.List;

public interface HallDao {
    Long save(Hall hall);

    List<Hall> findByTheaterId(Long theaterId);

    Long countById(Long id);

    Long countByTheaterIdAndName(Long theaterId, String name);
}
