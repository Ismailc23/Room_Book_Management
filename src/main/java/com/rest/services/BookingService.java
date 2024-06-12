package com.rest.services;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import com.rest.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public BookingEntity createBookings(Long customerId, Long roomNumber, BookingEntity bookings) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        Optional<RoomEntity> room = roomRepository.findById(roomNumber);
        if (!customer.isPresent() || !room.isPresent() || !room.get().isAvailable()) {
            return null;
        }
        else
        {
            bookings.setCustomer(customer.get());
            bookings.setRoom(room.get());
            bookings.setCustomerFirstName(customer.get().getFirstName());
            bookings.setCustomerLastName(customer.get().getLastName());
            bookings.setRoomType(room.get().getType());
            bookings.setRoomPrice(room.get().getPrice());
            bookingRepository.save(bookings);
            Date currentDate = new Date();
            if (currentDate.after(bookings.getStayStartDate()) && currentDate.before(bookings.getStayEndDate())) {
                room.get().setAvailable(false);
            } else {
                room.get().setAvailable(true);
            }
            roomRepository.save(room.get());
            return bookings;
        }
    }

    public Optional<BookingEntity> getBookingsByReferenceId(Long referenceId) {
        return bookingRepository.findById(referenceId);
    }
    public Optional<BookingEntity> getBookingsByCustomerIdAndRoomNumber(Long customerId, Long roomNumber) {
        return bookingRepository.findByCustomer_CustomerIdAndRoom_RoomNumber(customerId,roomNumber);
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
            Date currentDate = new Date();
            Optional<RoomEntity> room = roomRepository.findById(optionalBooking.get().getRoom().getRoomNumber());
            if(currentDate.after(bookingPatchDTO.getStayStartDate()) && currentDate.before(bookingPatchDTO.getStayEndDate())) {
                room.get().setAvailable(false);
            } else {
                room.get().setAvailable(true);
            }
            return bookingRepository.save(booking);
        }
        else
        {
            return null;
        }
    }
}
