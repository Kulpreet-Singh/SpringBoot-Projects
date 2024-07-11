package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.HallDao;
import com.kulpreet.bookmyticket.dao.TheaterDao;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.Hall;
import com.kulpreet.bookmyticket.model.Seat;
import com.kulpreet.bookmyticket.service.HallService;
import com.kulpreet.bookmyticket.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_THEATER;

@Service
public class HallServiceImpl implements HallService {
    @Autowired
    private HallDao hallDao;
    @Autowired
    private SeatService seatService;
    @Autowired
    private TheaterDao theaterDao;

    @Override
    public Long addHall(Hall hall) {
        if(theaterDao.countById(hall.getTheaterId()) == 0) {
            throw new ValidationException(NO_SUCH_THEATER.getCode(), NO_SUCH_THEATER.getMessage()+hall.getTheaterId());
        }
        if(hallDao.countByTheaterIdAndName(hall.getTheaterId(), hall.getName()) > 0) {
            throw new ValidationException("Hall with name: " + hall.getName() + " already exists in theater with id: " + hall.getTheaterId());
        }
        Long hallId = hallDao.save(hall);
        initializeHallSeats(hall, hallId);
        return hallId;

    }

    private void initializeHallSeats(Hall hall, Long hallId) {
        for(int i = 0; i < hall.getCapacityRow(); i++) {
            for(int j = 0; j < hall.getCapacityColumn(); j++) {
                Seat seat = new Seat();
                seat.setHallId(hallId);
                seat.setRow((char) ('A'+i));
                seat.setNumber(j + 1);
                seatService.save(seat);
            }
        }
    }

    @Override
    public List<Hall> getHalls(Long theaterId) {
        if(theaterDao.countById(theaterId) == 0) {
            throw new ValidationException(NO_SUCH_THEATER.getCode(), NO_SUCH_THEATER.getMessage()+theaterId);
        }
        return hallDao.findByTheaterId(theaterId);
    }

}
