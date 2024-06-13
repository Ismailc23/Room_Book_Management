package com.customer.contoller;

import com.customer.Entity.BookingsEntity;
import com.customer.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class BookingsController {

    @Autowired
    private BookingsService bookingsService;

    @PostMapping("/api/customers/{id}/{roomNumber}")
    public ResponseEntity<?> createBooking(@PathVariable("id") Long customerId, @PathVariable("roomNumber") Long roomNumber,@RequestBody BookingsEntity bookings) {
        BookingsEntity createdBooking = bookingsService.createBookings(customerId, roomNumber, bookings);
        Map<String, Object> responseBody = new HashMap<>();
        if (createdBooking != null) {
            responseBody.put("bookings", createdBooking);
            responseBody.put("error", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        }
        else
        {
            responseBody.put("bookings", null);
            responseBody.put("error", "Not able to create the booking");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @GetMapping("/bookings/{referenceId}")
    public ResponseEntity<?> getBookings(@PathVariable long referenceId)
    {
        Optional<BookingsEntity> bookings = bookingsService.getBookingsByReferenceId(referenceId);
        if(bookings.isPresent())
        {
            return ResponseEntity.ok().body(bookings.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("customers/{id}/{roomNumber}")
    public ResponseEntity<?> getBookingsByCustomerIdAndRoomNumber(@PathVariable("id") Long customerId,@PathVariable("roomNumber") Long roomNumber) {
        Optional<BookingsEntity> bookings = bookingsService.getBookingsByCustomerIdAndRoomNumber(customerId, roomNumber);
        if (bookings.isPresent())
        {
            return ResponseEntity.ok().body(bookings.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }

    }
}

