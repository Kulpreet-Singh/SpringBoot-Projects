package com.kulpreet.bookmyticket.exception;

public enum CustomExceptionCodes implements ExceptionCodes {
    NO_SUCH_CITY("BMT_400","No city found with id: "),
    NO_SUCH_THEATER("BMT_400","No theater found with id: "),
    NO_SUCH_USER("BMT_400","No user found with id: "),
    NO_SUCH_MOVIE("BMT_400","No movie found with id: "),
    NO_SUCH_HALL("BMT_400","No hall found with id: "),
    NO_SUCH_SHOW("BMT_400","No show found with id: "),
    USER_ALREADY_EXIST("BMT_400","User already exists with username or email or mobile"),
    SEAT_ALREADY_BOOKED("BMT_400","Seat is already booked: "),
    HALL_OCCUPIED("BMT_400", "Hall is occupied for the given time slot"),
    INVALID_SEAT("BMT_400", "Seat does not belong to the show. Invalid seat ids: "),
    INTERNAL_SERVER_ERROR("BMT_500", "Something went wrong!"),
    SHOW_ALREADY_STARTED("BMT_415","Cannot book seats now. Show has already started at: "),
    HOST_SHOW_PAST("BMT_415","Cannot host show in the past: ");

    private final String code;
    private final String message;

    CustomExceptionCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}