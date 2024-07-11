package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.CityDao;
import com.kulpreet.bookmyticket.dao.TheaterDao;
import com.kulpreet.bookmyticket.dao.UserDao;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.Theater;
import com.kulpreet.bookmyticket.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_CITY;

@Service
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    private TheaterDao theaterDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Long onboardTheater(Theater theater) {
        if(cityDao.countById(theater.getCityId()) == 0) {
            throw new ValidationException(NO_SUCH_CITY.getCode(), NO_SUCH_CITY.getMessage()+theater.getCityId());
        }
        if(theaterDao.countByCityIdAndName(theater.getCityId(), theater.getName()) > 0) {
            throw new ValidationException("Theater with name: " + theater.getName() + " already exists in city with id: " + theater.getCityId());
        }
        return theaterDao.save(theater);
    }

    @Override
    public List<Theater> findAll() {
        return theaterDao.findAll();
    }

    @Override
    public List<Theater> findByCityId(Long cityId) {
        if(cityDao.countById(cityId) == 0) {
            throw new ValidationException(NO_SUCH_CITY.getCode(), NO_SUCH_CITY.getMessage()+cityId);
        }
        return theaterDao.findByCityId(cityId);
    }
}


