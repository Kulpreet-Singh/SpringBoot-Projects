package com.kulpreet.bookmyticket.exception;

public interface ExceptionCodes {

    public String getCode();
    public String getMessage();
    String DEFAULT_VALIDATION_EXCEPTION_CODE = "BMT_400";
    String DEFAULT_VALIDATION_EXCEPTION_MESSAGE = "INVALID";
    String DEFAULT_FAILURE_EXCEPTION_CODE = "BMT_500";
    String DEFAULT_FAILURE_EXCEPTION_MESSAGE = "SOMETHING WENT WRONG";
}