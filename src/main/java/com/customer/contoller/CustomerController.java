package com.customer.contoller;

import com.customer.services.CustomerService;
import com.customer.Entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/request/api/customer", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerEntity customer) {
        Optional<CustomerEntity> response = Optional.ofNullable(customerService.createCustomer(customer));
        Map<String, Object> responseBody = new HashMap<>();
        if (response.isPresent()) {
            responseBody.put("customer", response.get());
            responseBody.put("error", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } else {
            responseBody.put("customer", null);
            responseBody.put("error", "Not able to create");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }
    @GetMapping("/request/api/customer/{id}")
    public ResponseEntity<CustomerEntity> getcustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id).map(customer -> ResponseEntity.ok().body(customer))
                .orElse(ResponseEntity.notFound().build());
    }
}