package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.dto.BookingInfo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingService {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Long bookSeats(Long userId, Long showId, List<Long> seatIds);

    List<BookingInfo> getBookings(boolean upcoming, Long userId);
}
