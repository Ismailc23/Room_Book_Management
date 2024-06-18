package com.rest.services;


import com.rest.Entity.BookingEntity;
import com.rest.Entity.CustomerEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);


    public boolean isCustomerAbove18(CustomerEntity customer) {
        LocalDate current = LocalDate.now();
        LocalDate dateOfBirth = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(dateOfBirth, current).getYears();
        logger.info("Age of the customer : {}", age );
        return age > 18;
    }

    public CustomerEntity createCustomer(CustomerEntity customer) {
        if(customerRepository.findByUserName(customer.getUserName()).isPresent() || !isCustomerAbove18(customer)) {
            logger.debug("Customer user name : {}" ,customer.getUserName());
            return null;
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedpwd = bcrypt.encode(customer.getPassword());
        customer.setPassword(encryptedpwd);
        customerRepository.save(customer);
        logger.debug("Customer is saved Successfully : {}",customer);
        return customer;
    }

    public Optional<CustomerEntity> getCustomerById(Long id) {

        return customerRepository.findById(id);
    }

    @Transactional
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        Optional<CustomerEntity> existCustomer = customerRepository.findById(customer.getCustomerId());
        if (!existCustomer.isPresent()) {
            logger.debug("Customer is not present");
            return null;
        }
        if(customerRepository.existsByUserName(customer.getUserName()))
        {
            logger.debug("Already present : {}",customer);
            return null;
        }
        customerRepository.save(customer);
        logger.debug("Customer is updated Successfully : {}",customer);
        return customer;
    }

    @Transactional
    public boolean deleteCustomer(Long id) {
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            logger.debug("Deleted customer");
            return true;
        }
        else {
            logger.debug("Customer is not found");
            return false;
        }
    }

}