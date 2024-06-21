package com.rest.Repository;

import com.rest.Entity.CustomerEntity;
import com.rest.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByUserName(String username);
    Optional<UserEntity> findByUserName(String userName);
}
