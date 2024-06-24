package com.rest.ExceptionHandling.CustomerExceptions;

public class InvalidAgeCustomerException extends RuntimeException {
    public InvalidAgeCustomerException(String message) {
        super(message);
    }
}
