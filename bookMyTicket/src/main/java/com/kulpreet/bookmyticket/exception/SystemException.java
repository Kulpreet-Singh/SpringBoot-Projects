package com.kulpreet.bookmyticket.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.kulpreet.bookmyticket.exception.ExceptionCodes.DEFAULT_FAILURE_EXCEPTION_CODE;
import static com.kulpreet.bookmyticket.exception.ExceptionCodes.DEFAULT_FAILURE_EXCEPTION_MESSAGE;

@Getter
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Map<String, String> errors = new HashMap<String, String>(3);
    private String code;
    private String message;

    public SystemException() {
        super(DEFAULT_FAILURE_EXCEPTION_CODE.concat(" : " + DEFAULT_FAILURE_EXCEPTION_MESSAGE));
        this.code = DEFAULT_FAILURE_EXCEPTION_CODE;
        this.message = DEFAULT_FAILURE_EXCEPTION_MESSAGE;
        errors.put(code, message);
    }

    public SystemException(String code, String message) {
        super(code.concat(" : " + message));
        this.code = code;
        this.message = message;
        errors.put(code, message);
    }

    public SystemException(String message) {
        super(DEFAULT_FAILURE_EXCEPTION_CODE.concat(" : " + message));
        this.code = DEFAULT_FAILURE_EXCEPTION_CODE;
        this.message = message;
        errors.put(code, message);
    }

    public SystemException(ExceptionCodes exception) {
        super(exception.getCode().concat(" : " + exception.getMessage()));
        this.code = exception.getCode();
        this.message = exception.getMessage();
        errors.put(exception.getCode(), exception.getMessage());
    }
}
