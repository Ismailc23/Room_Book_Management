package com.customer.services;


import com.customer.Entity.CustomerEntity;
import com.customer.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean isCustomerAbove18(CustomerEntity customer) {
        LocalDate current = LocalDate.now();
        LocalDate dateOfBirth = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(dateOfBirth, current).getYears();
        return age > 18;
    }
    public CustomerEntity createCustomer(CustomerEntity customer) {
        if (!isCustomerAbove18(customer)) {
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
}