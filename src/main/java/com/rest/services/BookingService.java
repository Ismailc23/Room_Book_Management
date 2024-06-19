package com.rest.services;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.ExceptionHandling.BookingExceptions.*;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import com.rest.Repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

     public BookingEntity createBookings (Long customerId, Long roomNumber, BookingEntity bookings) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        log.info("CustomerDetails {}", customer);
        Optional<RoomEntity> room = roomRepository.findById(roomNumber);
         log.info("Room Details {}", room);
         if (customer.isEmpty()) {
             throw new CustomerNotPresentException("Customer is not present with the give id : "+customerId);
         }
         if(room.isEmpty()) {
             throw new RoomNotPresentException("Room is not present with the given room number : "+roomNumber);
         }
         List<BookingEntity> existingBookings = bookingRepository.findOverlapBookings(room.get().getRoomNumber(), bookings.getStayEndDate(),bookings.getStayStartDate());
         if(!existingBookings.isEmpty()) {
             log.debug("Bookings Exist : {}" , existingBookings);
             throw new ExistOverlappingDatesException("The provided dates overlaps the already done booking date for the given room number : "+roomNumber);
         }
         if(bookings.getStayStartDate().isBefore(LocalDate.now()) || bookings.getStayEndDate().isBefore(bookings.getStayStartDate())) {
             log.debug("Invalid Dates given");
             throw new InvalidDateException("The date provided is invalid");
         }
         else {
            bookings.setCustomer(customer.get());
            bookings.setRoom(room.get());
            bookings.setCustomerFirstName(customer.get().getFirstName());
            bookings.setCustomerLastName(customer.get().getLastName());
            bookings.setRoomType(room.get().getType());
            bookings.setRoomPrice(room.get().getPrice());
            bookingRepository.save(bookings);
            log.debug("Bookings is made successfully : {}",bookings);
            return bookings;
        }
    }

    public Optional<BookingEntity> getBookingsByReferenceId(Long referenceId) {
        Optional<BookingEntity> getBooking = bookingRepository.findById(referenceId);
        if(!getBooking.isPresent()) {
            throw new BookingNotFoundException("Booking with the reference ID : "+referenceId+" is not present");
        }
        return getBooking;
    }

    public Optional<BookingEntity> getBookingsByCustomerIdAndRoomNumber(Long customerId, Long roomNumber) {
        Optional<BookingEntity> getBooking =bookingRepository.findByCustomerIdAndRoomNumber(customerId,roomNumber);
        if(!getBooking.isPresent()) {
            throw new BookingNotFoundException("Booking is not present with the provided customer id "+customerId+" and room number "+roomNumber);
        }
        return getBooking;
    }

    public BookingEntity patchBooking(Long id, BookingPatchDTO bookingPatchDTO) {
            Optional<BookingEntity> optionalBooking = bookingRepository.findById(id);

            if (optionalBooking.isPresent()) {
                BookingEntity booking = optionalBooking.get();

                if (bookingPatchDTO.getBookedDate() != null) {
                    booking.setBookedDate(bookingPatchDTO.getBookedDate());
                }
                if (bookingPatchDTO.getStayStartDate() != null) {
                    booking.setStayStartDate(bookingPatchDTO.getStayStartDate());
                }
                if (bookingPatchDTO.getStayEndDate() != null) {
                    booking.setStayEndDate(bookingPatchDTO.getStayEndDate());
                }
                log.info("Patch Booked Date : {}",bookingPatchDTO.getBookedDate());
                log.info("Patch Start Date : {}",bookingPatchDTO.getStayStartDate());
                log.info("Patch End Date : {}",bookingPatchDTO.getStayEndDate());
                return bookingRepository.save(booking);
            }
            else {
                return null;
            }
    }
}
