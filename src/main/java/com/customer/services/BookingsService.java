package com.customer.services;

import com.customer.Entity.BookingsEntity;
import com.customer.Entity.CustomerEntity;
import com.customer.Entity.RoomEntity;
import com.customer.Repository.BookingsRepository;
import com.customer.Repository.CustomerRepository;
import com.customer.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BookingsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    public BookingsEntity createBookings(Long customerId,Long roomNumber,BookingsEntity bookings) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            return null;
        }
        Optional<RoomEntity> room = roomRepository.findById(roomNumber);
        if (!room.isPresent() || !room.get().isAvailable()) {
            return null;
        }
        bookings.setCustomer(customer.get());
        bookings.setRoom(room.get());
        bookings.setCustomerFirstName(customer.get().getFirstName());
        bookings.setCustomerLastName(customer.get().getLastName());
        bookings.setRoomType(room.get().getType());
        bookings.setRoomPrice(room.get().getPrice());
        bookingsRepository.save(bookings);
        room.get().setAvailable(false);
        roomRepository.save(room.get());
        return bookings;
    }

    public Optional<BookingsEntity> getBookingsByReferenceId(Long referenceId)
    {
        return bookingsRepository.findById(referenceId);
    }

    public Optional<BookingsEntity> getBookingsByCustomerIdAndRoomNumber(Long customerId,Long roomNumber) {
        return bookingsRepository.findByCustomer_CustomerIdAndRoom_RoomNumber(customerId,roomNumber);
    }
}
