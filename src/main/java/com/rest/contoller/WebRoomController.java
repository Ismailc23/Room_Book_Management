package com.rest.contoller;

import com.rest.Entity.RoomEntity;
import com.rest.Repository.RoomRepository;
import com.rest.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class WebRoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    RestTemplate restTemplate = new RestTemplate();

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roomCreation")
    public String showRoomForm(Model model) {
        model.addAttribute("room", new RoomEntity());
        return "roomForm";
    }

    @PostMapping("/roomCreation")
    public String submitRoomForm(@ModelAttribute RoomEntity room) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8080/request/api/room", room, RoomEntity.class);
        return "redirect:/admin/roomCreation";
    }

    @GetMapping("/roomlist")
    public String showRoomsAdmin(Model model) {
        List<RoomEntity> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "RoomListAdmin";
    }

    @GetMapping("/updateRoomForm")
    public String showRoomUpdateForm(@RequestParam("roomNumber") Long roomNumber, Model model) {
        String url = "http://localhost:8080/request/api/room/" + roomNumber;
        RoomEntity room = restTemplate.getForObject(url, RoomEntity.class);
        model.addAttribute("room", room);
        return "updateRoom";
    }

    @PostMapping("/updateRoom")
    public String updateRoom(RoomEntity room, Model model) {
        String url = "http://localhost:8080/request/api/room/" + room.getRoomNumber();
        try {
            restTemplate.put(url, room);
            model.addAttribute("message", "Room updated successfully!");
        }
        catch (Exception e) {
            model.addAttribute("error", "Error updating room: " + e.getMessage());
        }
        return "redirect:/admin/roomlist";
    }

    @GetMapping("/deleteRoomForm")
    public String deleteRoom(@RequestParam("roomNumber") Long roomNumber, Model model) {
        String url = "http://localhost:8080/request/api/room/" + roomNumber;
        RoomEntity room = restTemplate.getForObject(url, RoomEntity.class);
        model.addAttribute("room", room);
        return "DeleteRoom";
    }

    @PostMapping("/deleteRoom")
    public String deleteRoom(RoomEntity room, Model model) {
        String url = "http://localhost:8080/request/api/room/" + room.getRoomNumber();
        try {
            restTemplate.delete(url);
            model.addAttribute("message", "Room deleted successfully!");
        }
        catch (Exception e) {
            model.addAttribute("error", "Error deleting room: " + e.getMessage());
        }
        return "redirect:/admin/roomlist";
    }
}
