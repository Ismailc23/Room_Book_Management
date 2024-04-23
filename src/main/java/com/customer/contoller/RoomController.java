package com.customer.contoller;

import com.customer.Entity.CustomerEntity;
import com.customer.Entity.RoomEntity;
import com.customer.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("request/api/room")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRoom(@RequestBody RoomEntity room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(room));
    }

    @GetMapping("request/api/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable long id) {
       if(roomService.getRoomById(id).isPresent()){
           return ResponseEntity.ok().body(roomService.getRoomById(id).get());
       }else {
           return ResponseEntity.notFound().build();
       }
    }
}
