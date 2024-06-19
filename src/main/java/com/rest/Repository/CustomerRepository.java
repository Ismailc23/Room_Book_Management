package com.rest.Repository;

import com.rest.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUserName(String userName);

    boolean existsByUserName(String username);
}
