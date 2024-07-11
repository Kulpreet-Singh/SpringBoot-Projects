package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.City;

import java.util.List;

public interface CityDao {
    Long countById(Long id);

    Long countByName(String name);

    Long save(City city);

    List<City> findAll();

    City findById(Long id);
}
