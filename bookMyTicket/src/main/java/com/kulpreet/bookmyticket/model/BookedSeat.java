package com.kulpreet.bookmyticket.model;

import lombok.Data;

@Data
public class BookedSeat {
    private Long id;
    private Long bookingId;
    private Long showId;
    private Long seatId;
}
