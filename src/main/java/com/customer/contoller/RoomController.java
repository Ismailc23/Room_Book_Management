package com.customer.contoller;


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
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<RoomEntity> createroom(@RequestBody RoomEntity room){
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("request/api/room/{id}")
    public Optional<RoomEntity> getRoom(@PathVariable long id) {
        return roomService.getRoomById(id);
    }

}
