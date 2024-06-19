package com.rest.ExceptionHandling.BookingExceptions;

public class CustomerNotPresentException extends RuntimeException {
    public CustomerNotPresentException(String message) {
        super(message);
    }
}
