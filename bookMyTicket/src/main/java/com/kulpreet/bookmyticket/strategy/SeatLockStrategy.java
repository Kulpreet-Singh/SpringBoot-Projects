package com.kulpreet.bookmyticket.strategy;

public interface SeatLockStrategy {
    boolean lockSeat(Long showId, Long seatId, String userId);
    boolean unlockSeat(Long showId, Long seatId, String userId);
    boolean isSeatLocked(Long showId, Long seatId, String userId);
}
