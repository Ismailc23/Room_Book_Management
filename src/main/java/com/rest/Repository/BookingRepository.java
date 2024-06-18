package com.rest.Repository;

import com.rest.Entity.BookingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @Transactional
    List<BookingEntity> findByCustomer_CustomerId(Long customerId);

    Optional<BookingEntity> findByCustomer_CustomerIdAndRoom_RoomNumber(Long customerId, Long roomNumber);

    Optional<BookingEntity> findByRoom_RoomNumber(Long roomNumber);

    BookingEntity getByCustomer_CustomerId(Long customerId);

    BookingEntity getByRoom_RoomNumber(Long roomNumber);

    void deleteByCustomer_CustomerId(Long customerId);

    void deleteByRoom_RoomNumber(Long roomNumber);
}
