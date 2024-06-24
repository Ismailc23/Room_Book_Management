package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoomUIController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public String showRoomForm(Model model)
    {
        model.addAttribute("room", new RoomEntity());
        return "roomForm";
    }

    @PostMapping("/rooms")
    public String submitRoomForm(RoomEntity room)
    {
        roomRepository.save(room);
        return "redirect:/rooms";
    }
}
