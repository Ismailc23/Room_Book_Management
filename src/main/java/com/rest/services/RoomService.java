package com.rest.services;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public RoomEntity createRoom(RoomEntity room){
        if(roomRepository.findById(room.getRoomNumber()).isPresent()) {
            return null;
        }
        roomRepository.save(room);
        return room;
    }

    public Optional<RoomEntity> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    @Transactional
    public RoomEntity updateRoom(RoomEntity room) {
        Optional<RoomEntity> existRoom = roomRepository.findById(room.getRoomNumber());
        if(!existRoom.isPresent()) {
            return null;
        }
        return roomRepository.save(room);
    }

    @Transactional
    public boolean deleteRoom(Long roomNumber) {
        if(roomRepository.existsById(roomNumber)) {
            roomRepository.deleteById(roomNumber);
            return true;
        }
        else {
            return false;
        }
    }
}
