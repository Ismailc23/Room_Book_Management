package com.customer.Repository;

import com.customer.Entity.CustomerRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRoomRepository extends JpaRepository<CustomerRoomEntity, Long> {
}
