package com.rest.contoller;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/api/customers/{id}/{roomNumber}")
    public ResponseEntity<?> createBooking(@PathVariable("id") Long customerId, @PathVariable("roomNumber") Long roomNumber,@RequestBody BookingEntity bookings) {
        BookingEntity createdBooking = bookingService.createBookings(customerId, roomNumber, bookings);
        if (createdBooking != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not able to create the booking");
        }
    }

    @GetMapping("/bookings/{referenceId}")
    public ResponseEntity<?> getBookings(@PathVariable long referenceId) {
        Optional<BookingEntity> bookings = bookingService.getBookingsByReferenceId(referenceId);
        if(bookings.isPresent()) {
            return ResponseEntity.ok().body(bookings.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("customers/{id}/{roomNumber}")
    public ResponseEntity<?> getBookingByCustomerIdAndRoomNumber(@PathVariable("id") Long customerId,@PathVariable("roomNumber") Long roomNumber) {
        Optional<BookingEntity> bookings = bookingService.getBookingsByCustomerIdAndRoomNumber(customerId, roomNumber);
        if (bookings.isPresent()) {
            return ResponseEntity.ok().body(bookings.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchBooking(@PathVariable Long id, @RequestBody BookingPatchDTO bookingPatchDTO) {
        Optional<BookingEntity> patchBooking = Optional.ofNullable(bookingService.patchBooking(id, bookingPatchDTO));
        if(patchBooking.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(patchBooking.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not able to update the Booking");
        }
    }
}

