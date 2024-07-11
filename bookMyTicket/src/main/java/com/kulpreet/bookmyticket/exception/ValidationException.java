package com.kulpreet.bookmyticket.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.kulpreet.bookmyticket.exception.ExceptionCodes.DEFAULT_VALIDATION_EXCEPTION_CODE;
import static com.kulpreet.bookmyticket.exception.ExceptionCodes.DEFAULT_VALIDATION_EXCEPTION_MESSAGE;

@Getter
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Map<String, String> errors = new HashMap<String, String>(3);
    private String code;
    private String message;

    public ValidationException() {
        super(DEFAULT_VALIDATION_EXCEPTION_CODE.concat(" : " + DEFAULT_VALIDATION_EXCEPTION_MESSAGE));
        this.code = DEFAULT_VALIDATION_EXCEPTION_CODE;
        this.message = DEFAULT_VALIDATION_EXCEPTION_MESSAGE;
        errors.put(code, message);
    }

    public ValidationException(String code, String message) {
        super(code.concat(" : " + message));
        this.code = code;
        this.message = message;
        errors.put(code, message);
    }

    public ValidationException(String message) {
        super(DEFAULT_VALIDATION_EXCEPTION_CODE.concat(" : " + message));
        this.code = DEFAULT_VALIDATION_EXCEPTION_CODE;
        this.message = message;
        errors.put(code, message);
    }

    public ValidationException(ExceptionCodes exception) {
        super(exception.getCode().concat(" : " + exception.getMessage()));
        this.code = exception.getCode();
        this.message = exception.getMessage();
        errors.put(exception.getCode(), exception.getMessage());
    }

}