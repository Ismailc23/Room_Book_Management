package com.rest.contoller;


import com.rest.Entity.RoomEntity;
import com.rest.services.RoomService;
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
    public ResponseEntity<?> createRoom(@RequestBody RoomEntity room) {
        Optional<RoomEntity> response = Optional.ofNullable(roomService.createRoom(room));
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not able to create");
        }
    }

    @GetMapping("request/api/room/{id}")
    public ResponseEntity<RoomEntity> getRoom(@PathVariable long id) {
       if(roomService.getRoomById(id).isPresent()){
           return ResponseEntity.ok().body(roomService.getRoomById(id).get());
       }
       else {
           return ResponseEntity.notFound().build();
       }
    }

    @PutMapping("request/api/{roomNumber}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomNumber,@RequestBody RoomEntity room) {
        room.setRoomNumber(roomNumber);
        Optional<RoomEntity> response = Optional.ofNullable(roomService.updateRoom(room));
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not able to update");
        }
    }

    @DeleteMapping("request/{roomNumber}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomNumber) {
        return roomService.deleteRoom(roomNumber) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
