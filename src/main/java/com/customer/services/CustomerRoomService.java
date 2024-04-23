package com.customer.services;

import com.customer.Entity.CustomerEntity;
import com.customer.Entity.CustomerRoomEntity;
import com.customer.Repository.CustomerRepository;
import com.customer.Repository.CustomerRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRoomService {

    private final CustomerRoomRepository customerRoomRepository;

    public CustomerRoomService(CustomerRoomRepository customerRoomRepository) {
        this.customerRoomRepository = customerRoomRepository;
    }

    public CustomerRoomEntity createCustomerRoom(CustomerRoomEntity customerRoom) {
        return customerRoomRepository.save(customerRoom);
    }
    public Optional<CustomerRoomEntity> getBookById(Long id) {
        return customerRoomRepository.findById(id);
    }
}
