package com.rest.ExceptionHandling.CustomerExceptions;

public class UserNameAlreadyException extends RuntimeException {
    public UserNameAlreadyException(String message) {
        super(message);
    }
}
