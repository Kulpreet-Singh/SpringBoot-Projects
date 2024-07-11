package com.kulpreet.bookmyticket.strategy.impl;

import com.kulpreet.bookmyticket.strategy.SeatLockStrategy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class NoSeatLockStrategy implements SeatLockStrategy {
    @Override
    public boolean lockSeat(Long showId, Long seatId, String userId) {
        return true;
    }

    @Override
    public boolean unlockSeat(Long showId, Long seatId, String userId) {
        return true;
    }

    @Override
    public boolean isSeatLocked(Long showId, Long seatId, String userId) {
        return false;
    }
}
