package com.rest.ExceptionHandling.BookingExceptions;

import com.rest.Entity.RoomEntity;

public class RoomNotPresentException extends RuntimeException {
    public RoomNotPresentException(String message) {
        super(message);
    }
}
