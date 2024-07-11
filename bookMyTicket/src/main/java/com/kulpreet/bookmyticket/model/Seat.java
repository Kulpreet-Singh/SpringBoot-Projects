package com.kulpreet.bookmyticket.model;

import lombok.Data;

@Data
public class Seat {
    private Long id;
    private Long hallId;
    private char row; // A-Z
    private int number; // 1-25
    private boolean isBooked;
    private String seatNumber;
    public void setSeatNumber() {
        seatNumber = row + "-" + number;
    }
    public void setRow(char row) {
        this.row = row;
        setSeatNumber();
    }

    public void setNumber(int number) {
        this.number = number;
        setSeatNumber();
    }
}

