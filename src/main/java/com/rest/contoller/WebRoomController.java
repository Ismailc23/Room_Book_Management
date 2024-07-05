package com.rest.contoller;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.CustomerEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.RoomRepository;
import com.rest.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WebRoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/roomCreation")
    public String showRoomForm(Model model) {
        model.addAttribute("room", new RoomEntity());
        return "roomForm";
    }

    @PostMapping("/roomCreation")
    public String submitRoomForm(@ModelAttribute RoomEntity room) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8080/request/api/room", room, RoomEntity.class);
        return "redirect:/roomCreation";
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
        String url = "http://localhost:8080/request/api/" + room.getRoomNumber();
        try {
            restTemplate.put(url, room);
            model.addAttribute("message", "Room updated successfully!");
        }
        catch (Exception e) {
            model.addAttribute("error", "Error updating room: " + e.getMessage());
        }
        return "redirect:/roomlist";
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
        String url = "http://localhost:8080/request/" + room.getRoomNumber();
        try {
            restTemplate.delete(url);
            model.addAttribute("message", "Room deleted successfully!");
        }
        catch (Exception e) {
            model.addAttribute("error", "Error deleting room: " + e.getMessage());
        }
        return "redirect:/roomCreation";
    }
}
