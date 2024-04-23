package com.customer.contoller;

import com.customer.services.CustomerService;
import com.customer.Entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/request/api/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerEntity customer) {
        CustomerEntity response = customerService.createCustomer(customer);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/request/api/customer/{id}")
    public ResponseEntity<?> getcustomer(@PathVariable long id) {
        return customerService.getCustomerById(id).map(customer -> ResponseEntity.ok().body(customer))
                .orElse(ResponseEntity.notFound().build());
    }
}