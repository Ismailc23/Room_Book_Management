package com.rest.contoller;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.ExceptionHandling.BookingExceptions.*;
import com.rest.ExceptionHandling.CustomerExceptions.CustomerNotFoundException;
import com.rest.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/api/customers/{id}/{roomNumber}")
    public ResponseEntity<?> createBooking(@PathVariable("id") Long customerId, @PathVariable("roomNumber") Long roomNumber,@RequestBody BookingEntity bookings) {
        try {
            BookingEntity createdBooking = bookingService.createBookings(customerId, roomNumber, bookings);
            log.debug("Booking is done successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        }
        catch (CustomerNotPresentException | InvalidDateException | RoomNotPresentException e) {
            log.error("Booking is not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(ExistOverlappingDatesException e) {
            log.error("Dates provided are overlapping with the bookings in the database");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        }

    @GetMapping("/bookings/{referenceId}")
    public ResponseEntity<?> getBookings(@PathVariable long referenceId) {
        try {
            Optional<BookingEntity> bookings = bookingService.getBookingsByReferenceId(referenceId);
            log.debug("Fetching booking succesfful");
            return ResponseEntity.status(HttpStatus.OK).body(bookings.get());
        }
        catch(BookingNotFoundException e) {
            log.error("Booking is not found to fetch");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("customers/{id}/{roomNumber}")
    public ResponseEntity<?> getBookingByCustomerIdAndRoomNumber(@PathVariable("id") Long customerId,@PathVariable("roomNumber") Long roomNumber) {
        try {
            Optional<BookingEntity> bookings = bookingService.getBookingsByCustomerIdAndRoomNumber(customerId, roomNumber);
            log.debug("Fetching booking succesful");
            return ResponseEntity.status(HttpStatus.OK).body(bookings.get());
        }
        catch(BookingNotFoundException e) {
            log.error("Booking is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchBooking(@PathVariable Long id, @RequestBody BookingPatchDTO bookingPatchDTO) {
        Optional<BookingEntity> patchBooking = Optional.ofNullable(bookingService.patchBooking(id, bookingPatchDTO));
        if(patchBooking.isPresent()) {
            return ResponseEntity.ok().body(patchBooking.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Not able to update the Booking");
        }
    }
}

