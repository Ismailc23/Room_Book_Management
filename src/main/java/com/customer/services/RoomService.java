package com.customer.services;

import com.customer.Entity.RoomEntity;
import com.customer.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomEntity createRoom(RoomEntity room){
        if(roomRepository.findByRoomNumber(room.getRoomNumber()).isPresent())
        {
            return null;
        }
        roomRepository.save(room);
        return room;
    }
    public Optional<RoomEntity> getRoomById(Long id)
    {
        return roomRepository.findById(id);
    }

    public RoomEntity updateRoom(RoomEntity room)
    {
        Optional<RoomEntity> existRoom = roomRepository.findByRoomNumber(room.getRoomNumber());
        if(!existRoom.isPresent())
        {
            return null;
        }
        return roomRepository.save(room);
    }

    public boolean deleteRoom(Long roomNumber)
    {
        if(roomRepository.existsByRoomNumber(roomNumber))
        {
            roomRepository.deleteById(roomNumber);
            return true;
        }
        else {
            return false;
        }
    }
}
