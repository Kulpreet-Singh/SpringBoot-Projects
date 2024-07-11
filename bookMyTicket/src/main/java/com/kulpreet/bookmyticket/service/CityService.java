package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.model.City;

import java.util.List;

public interface CityService {
    Long save(City city);

    List<City> findAll();

    City findById(Long id);
}
