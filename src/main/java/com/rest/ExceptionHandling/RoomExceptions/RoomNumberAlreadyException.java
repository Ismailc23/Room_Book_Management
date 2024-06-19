package com.rest.ExceptionHandling.RoomExceptions;

public class RoomNumberAlreadyException extends RuntimeException {
    public RoomNumberAlreadyException(String message) {
        super(message);
    }
}
