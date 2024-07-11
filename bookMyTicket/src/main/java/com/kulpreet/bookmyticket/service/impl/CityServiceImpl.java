package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.CityDao;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.City;
import com.kulpreet.bookmyticket.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_CITY;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao cityDao;

    @Override
    public Long save(City city) {
        if(cityDao.countByName(city.getName()) > 0) {
            throw new ValidationException("City with name: " + city.getName() + " already exists");
        }
        return cityDao.save(city);
    }

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }

    @Override
    public City findById(Long id) {
        return cityDao.findById(id);
    }
}

