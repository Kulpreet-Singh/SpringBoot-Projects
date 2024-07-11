package com.kulpreet.bookmyticket.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Booking {
    private Long id;
    private Long userId;
    private LocalDateTime bookingTime;
}