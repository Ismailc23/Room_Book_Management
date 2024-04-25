package com.customer.contoller;

import com.customer.Entity.CustomerEntity;
import com.customer.Entity.RoomEntity;
import com.customer.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("request/api/room")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRoom(@RequestBody RoomEntity room) {
        Optional<RoomEntity> response = Optional.ofNullable(roomService.createRoom(room));
        Map<String, Object> responseBody = new HashMap<>();
        if (response.isPresent()) {
            responseBody.put("room", response.get());
            responseBody.put("error", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        }
        return null;
    }

    @GetMapping("request/api/room/{id}")
    public ResponseEntity<RoomEntity> getRoom(@PathVariable long id) {
       if(roomService.getRoomById(id).isPresent()){
           return ResponseEntity.ok().body(roomService.getRoomById(id).get());
       }else {
           return ResponseEntity.notFound().build();
       }
    }
}
