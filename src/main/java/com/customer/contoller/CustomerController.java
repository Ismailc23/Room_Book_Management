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

    @PostMapping("request/api/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerEntity customer) {
        ResponseEntity<?> response = customerService.createCustomer(customer);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
        } else {
            return response;
        }
    }

    @GetMapping("request/api/customer/{id}")
    public ResponseEntity<?> getcustomer(@PathVariable long id) {
        return customerService.getCustomerById(id).map(customer -> ResponseEntity.ok().body(customer))
                .orElse(ResponseEntity.notFound().build());
    }
}