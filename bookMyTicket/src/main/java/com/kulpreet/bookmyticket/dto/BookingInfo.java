package com.kulpreet.bookmyticket.dto;

import lombok.Data;

@Data
public class BookingInfo extends ShowInfo{
    private Long bookingId;
    private String username;
    private String bookingTime;
    private String seatNumber;

    public void setShowInfo(ShowInfo showInfo) {
        if(this.getShowId().equals(showInfo.getShowId())) {
            this.setMovieName(showInfo.getMovieName());
            this.setShowTime(showInfo.getShowTime());
            this.setTheaterName(showInfo.getTheaterName());
            this.setCity(showInfo.getCity());
            this.setHallName(showInfo.getHallName());
        }
    }
}
