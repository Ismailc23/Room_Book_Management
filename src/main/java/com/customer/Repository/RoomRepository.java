package com.customer.Repository;

import com.customer.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Optional<RoomEntity> findByRoomNumber(Long roomNumber);
    boolean existsByRoomNumber(Long roomNumber);
}
