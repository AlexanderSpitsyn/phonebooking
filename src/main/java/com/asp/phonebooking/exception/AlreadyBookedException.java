package com.asp.phonebooking.exception;

public class AlreadyBookedException extends RuntimeException {
    public AlreadyBookedException(String message) {
        super(message);
    }
}
