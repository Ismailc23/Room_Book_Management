package com.rest.services;

import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);


    public RoomEntity createRoom(RoomEntity room){
        if(roomRepository.findById(room.getRoomNumber()).isPresent()) {
            logger.debug("Room number already present : {}",room.getRoomNumber());
            return null;
        }
        roomRepository.save(room);
        logger.debug("Room Saved successfully");
        return room;
    }

    public Optional<RoomEntity> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    @Transactional
    public RoomEntity updateRoom(RoomEntity room) {
        Optional<RoomEntity> existRoom = roomRepository.findById(room.getRoomNumber());
        if(!existRoom.isPresent()) {
            logger.debug("Room is not present");
            return null;
        }
        logger.debug("Room saved successfully");
        return roomRepository.save(room);
    }

    @Transactional
    public boolean deleteRoom(Long roomNumber) {
        if(roomRepository.existsById(roomNumber)) {
            roomRepository.deleteById(roomNumber);
            logger.debug("Room is deleted successfully");
            return true;
        }
        else {
            logger.debug("Room can't be deleted");
            return false;
        }
    }
}
