package com.kulpreet.bookmyticket.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> validationFailed(ValidationException ex) {
        return ex.getErrors();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> systemFailed(SystemException ex) {
        return ex.getErrors();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExecutionException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Map<String, String> remoteCommunicationFailed(ExecutionException ex) {
        log.error("Failed communicating with remote service.", ex);
        Map<String, String> error = new HashMap<String, String>(1);
        error.put("remote_comm_failure", ExceptionUtils.getRootCauseMessage(ex));
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> internalError(Exception ex) {
        log.error("Internal Server Error", ex);
        Map<String, String> error = new HashMap<String, String>(1);
        error.put("internal_error", ExceptionUtils.getRootCauseMessage(ex));
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleDataAccessException(DataAccessException ex) {
        log.error("### handling DataAccessException : {} ###", ex);
        Map<String, String> error = new HashMap<String, String>(1);
        error.put("message", "Error while executing the DB query. " + ExceptionUtils.getRootCauseMessage(ex));
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.error("### handling EmptyResultDataAccessException : {} ###", ex);
        Map<String, String> error = new HashMap<String, String>(1);
        error.put("message", "No data found in the database.");
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SecurityException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleSecurityException(SecurityException ex) {
        log.error("### handling SecurityException : {} ###", ex);
        Map<String, String> error = new HashMap<String, String>(1);
        error.put("message", "Error occured while executing the request.");
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("### HttpMessageNotReadableException : {} ###", ex.getMessage());
        Map<String, String> error = new HashMap<String, String>(2);
        error.put("message", "Malformed request");
        error.put("error", ExceptionUtils.getRootCauseMessage(ex));
        return error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> argumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        Map<String, String> errorList = new HashMap<>(1);
        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.add(error.getField() + " - " + error.getDefaultMessage()));
        log.error("Validation failed: {}", details);
        if (details.size() > 0)
            errorList.put("BMT_400", StringUtils.join(details, ", "));
        return errorList;
    }
}

