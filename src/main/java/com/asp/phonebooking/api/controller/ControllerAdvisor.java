package com.asp.phonebooking.api.controller;

import com.asp.phonebooking.exception.AlreadyBookedException;
import com.asp.phonebooking.exception.AlreadyExistsException;
import com.asp.phonebooking.exception.InvalidBookingException;
import com.asp.phonebooking.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exc, WebRequest req) {
        return handleException(HttpStatus.NOT_FOUND, exc, req);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exc, WebRequest req) {
        return handleException(HttpStatus.BAD_REQUEST, exc, req);
    }

    @ExceptionHandler(AlreadyBookedException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyBookedException exc, WebRequest req) {
        return handleException(HttpStatus.CONFLICT, exc, req);
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(InvalidBookingException exc, WebRequest req) {
        return handleException(HttpStatus.BAD_REQUEST, exc, req);
    }

    private ResponseEntity<Object> handleException(HttpStatus httpStatus, Exception exc, WebRequest req) {
        var errorResponse = ErrorResponse.create(exc, httpStatus, exc.getMessage());
        return handleExceptionInternal(exc, errorResponse, HttpHeaders.EMPTY, httpStatus, req);
    }
}
