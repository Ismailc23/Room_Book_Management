package com.customer.services;

import com.customer.Entity.RoomEntity;
import com.customer.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(RoomEntity room) {
        roomRepository.save(room);
    }

    public Optional<RoomEntity> getRoomById(Long id) {
        return roomRepository.findById(id);
    }
}
