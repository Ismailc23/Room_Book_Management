package com.customer.Repository;

import com.customer.Entity.BookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingsRepository extends JpaRepository<BookingsEntity, Long> {
    Optional<BookingsEntity> findByCustomer_CustomerIdAndRoom_RoomNumber(Long customerId, Long roomNumber);
}
