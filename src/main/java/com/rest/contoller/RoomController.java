package com.rest.contoller;


import com.rest.Entity.RoomEntity;
import com.rest.ExceptionHandling.RoomExceptions.RoomNotFoundException;
import com.rest.ExceptionHandling.RoomExceptions.RoomNumberAlreadyExistException;
import com.rest.services.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("request/api/room")
    public ResponseEntity<?> createRoom(@RequestBody RoomEntity room) {
            RoomEntity createdRoom = roomService.createRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping("request/api/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable long id) {
           Optional<RoomEntity> room = roomService.getRoomById(id);
           log.debug("Room is present with Room number : {}",id);
           return ResponseEntity.status(HttpStatus.OK).body(room.get());
    }

    @PutMapping("request/api/room/{roomNumber}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomNumber,@RequestBody RoomEntity room) {
        room.setRoomNumber(roomNumber);
        RoomEntity updatedRoom = roomService.updateRoom(room);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
    }

    @DeleteMapping("request/api/room/{roomNumber}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomNumber) {
        roomService.deleteRoom(roomNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted the room successfully");
        }
}
