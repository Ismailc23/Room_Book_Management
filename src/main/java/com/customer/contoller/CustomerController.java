package com.customer.contoller;

import com.customer.services.CustomerService;
import com.customer.Entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("request/api/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerEntity customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @GetMapping("request/api/customer/{id}")
    public ResponseEntity<?> getcustomer(@PathVariable long id) {
        Optional<CustomerEntity> customerOptional = customerService.getCustomerById(id);
        return customerOptional.map(customer -> ResponseEntity.ok().body(customer))
                .orElse(ResponseEntity.notFound().build());
    }
}