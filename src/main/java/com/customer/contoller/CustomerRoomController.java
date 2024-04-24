package com.customer.contoller;

import com.customer.Entity.CustomerRoomEntity;
import com.customer.services.CustomerRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerRoomController {

    @Autowired
    private CustomerRoomService customerRoomService;

    public CustomerRoomController(CustomerRoomService customerRoomService) {
        this.customerRoomService = customerRoomService;
    }

    @PostMapping("/api/customerRoom")
    public ResponseEntity<?> createBooking(@RequestBody CustomerRoomEntity customerRoom) {
        CustomerRoomEntity createCustomerRoom = customerRoomService.createCustomerRoom(customerRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCustomerRoom);
    }

    @GetMapping("request/api/customerRoom/{id}")
    public ResponseEntity<?> getBook(@PathVariable long id) {
        if(customerRoomService.getBookById(id).isPresent()){
            return ResponseEntity.ok().body(customerRoomService.getBookById(id).get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}

