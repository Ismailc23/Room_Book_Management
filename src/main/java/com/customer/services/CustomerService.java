package com.customer.services;

//import com.customer.Repository.CustomerRepository;
import com.customer.Entity.CustomerEntity;
import com.customer.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    public String createCustomer(CustomerEntity customer) {
//        if ((customer.getPassword().length() < 8 || customer.getPassword().length() > 10) || (existsByUsername(customer.getUsername()))
        try {
            if (customer.getPassword().length() < 8 || customer.getPassword().length() > 10) {
                return "Password length should be between 8 and 10(inclusive)";
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

    public Optional<CustomerEntity> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }
}
