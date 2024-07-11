package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.Theater;

import java.util.List;

public interface TheaterDao {
    Long save(Theater theater);

    List<Theater> findAll();

    List<Theater> findByCityId(Long cityId);

    Long countById(Long id);

    Long countByCityIdAndName(Long cityId, String name);
}
