package com.asp.phonebooking.api.controller;

import com.asp.phonebooking.exception.AlreadyExistsException;
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
        var errorResponse = ErrorResponse.create(exc, HttpStatus.NOT_FOUND, exc.getMessage());
        return handleExceptionInternal(exc, errorResponse, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exc, WebRequest req) {
        var errorResponse = ErrorResponse.create(exc, HttpStatus.BAD_REQUEST, exc.getMessage());
        return handleExceptionInternal(exc, errorResponse, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, req);
    }
}
