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

    @PostMapping("/request/api/customer")
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @GetMapping("/request/api/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
      if(customerService.getCustomerById(id).isPresent()){
            return ResponseEntity.ok().body(customerService.getCustomerById(id).get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/request/api/customer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,@RequestBody CustomerEntity customer)
    {
        customer.setCustomerId(id);
        Optional<CustomerEntity> response = Optional.ofNullable(customerService.updateCustomer(customer));
        Map<String, Object> responseBody = new HashMap<>();
        if (response.isPresent()) {
            responseBody.put("customer", response.get());
            responseBody.put("error", null);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } else {
            responseBody.put("customer", null);
            responseBody.put("error", "Not able to Update");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }
    }

    @DeleteMapping("/request/api/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id)
    {
        return customerService.deleteCustomer(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}