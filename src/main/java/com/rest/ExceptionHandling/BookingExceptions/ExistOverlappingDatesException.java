package com.rest.ExceptionHandling.BookingExceptions;

public class ExistOverlappingDatesException extends RuntimeException {
    public ExistOverlappingDatesException(String message) {
        super(message);
    }
}
