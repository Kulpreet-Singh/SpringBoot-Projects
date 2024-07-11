package com.kulpreet.bookmyticket.dto;

import lombok.Data;

@Data
public class ShowInfo {
    private Long showId;
    private String movieName;
    private String showTime;
    private String theaterName;
    private String city;
    private String hallName;
}
