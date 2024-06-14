package com.rest.services;


import com.rest.Entity.BookingEntity;
import com.rest.Entity.CustomerEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public boolean isCustomerAbove18(CustomerEntity customer) {
        LocalDate current = LocalDate.now();
        LocalDate dateOfBirth = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(dateOfBirth, current).getYears();
        return age > 18;
    }

    public CustomerEntity createCustomer(CustomerEntity customer) {
        if(customerRepository.findByUserName(customer.getUserName()).isPresent() || !isCustomerAbove18(customer)) {
            return null;
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedpwd = bcrypt.encode(customer.getPassword());
        customer.setPassword(encryptedpwd);
        customerRepository.save(customer);
        return customer;
    }

    public Optional<CustomerEntity> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        Optional<CustomerEntity> existCustomer = customerRepository.findById(customer.getCustomerId());
        if (!existCustomer.isPresent() || customerRepository.existsByUserName(customer.getUserName())) {
            return null;
        }
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public boolean deleteCustomer(Long id) {
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

}