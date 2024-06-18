package com.rest.services;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.BookingPatchDTO;
import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import com.rest.Repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;


     public BookingEntity createBookings (Long customerId, Long roomNumber, BookingEntity bookings){
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        logger.info("CustomerDetails {}", customer);
        Optional<RoomEntity> room = roomRepository.findById(roomNumber);
         logger.info("Room Details {}", room);
         if (customer.isEmpty() || room.isEmpty()) {
             return null;
         }
         List<BookingEntity> existingBookings = bookingRepository.findByRoom_RoomNumberAndStayStartDateLessThanEqualAndStayEndDateGreaterThanEqual(room.get().getRoomNumber(), bookings.getStayEndDate(),bookings.getStayStartDate());
         if(!existingBookings.isEmpty())
         {
             logger.info("Bookings Exist : {}" , existingBookings);
             return null;
         }
         if(bookings.getStayStartDate().isBefore(LocalDate.now()) || bookings.getStayEndDate().isBefore(bookings.getStayStartDate()))
         {
             logger.info("Validate Dates");
             return null;
         }
         else {
            bookings.setCustomer(customer.get());
            bookings.setRoom(room.get());
            bookings.setCustomerFirstName(customer.get().getFirstName());
            bookings.setCustomerLastName(customer.get().getLastName());
            bookings.setRoomType(room.get().getType());
            bookings.setRoomPrice(room.get().getPrice());
            bookingRepository.save(bookings);
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
                return bookingRepository.save(booking);
            } else {
                return null;
            }
    }

}
