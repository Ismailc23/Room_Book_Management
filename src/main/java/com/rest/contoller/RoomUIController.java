package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.RoomRepository;
import com.rest.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoomUIController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("/roomform")
    public String showRoomForm(Model model)
    {
        model.addAttribute("room", new RoomEntity());
        return "roomForm";
    }

    @PostMapping("/roomform")
    public String submitRoomForm(RoomEntity room)
    {
        roomRepository.save(room);
        return "redirect:/roomform";
    }


    @GetMapping("/rooms")
    public String showRoomsUser(Model model) {
        List<RoomEntity> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("/roomlist")
    public String showRoomsAdmin(Model model) {
        List<RoomEntity> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "RoomListAdmin";
    }

    @DeleteMapping("/roomlist")
    public void deleteRooms() {

    }
}
