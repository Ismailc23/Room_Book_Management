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
    public void createCustomer(CustomerEntity customer) {
        customerRepository.save(customer);
   }

    public Optional<CustomerEntity> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
}
