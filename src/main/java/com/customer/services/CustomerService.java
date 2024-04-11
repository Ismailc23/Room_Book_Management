package com.customer.services;

import com.customer.CustomerRepository;
import com.customer.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    public String createCustomer(Customer customer) {
        try {
            if (customer.getPassword().length() < 8 || customer.getPassword().length() > 10) {
                return "Password length should be between 8 and 10";
            }
            if (existsByUsername(customer.getUsername())) {
                return "Username already exists";
            }
            customerRepository.save(customer);
            return "Customer Saved";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }
}
