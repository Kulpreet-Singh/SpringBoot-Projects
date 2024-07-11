package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.dto.BookingInfo;
import com.kulpreet.bookmyticket.model.Booking;

import java.util.List;

public interface BookingDao {
    Long save(Booking booking);

    List<BookingInfo> findByUserId(boolean upcoming, Long userId);
}
