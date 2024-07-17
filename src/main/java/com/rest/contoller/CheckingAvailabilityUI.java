package com.rest.contoller;

import com.rest.Entity.RoomEntity;
import com.rest.services.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CheckingAvailabilityUI {

    @Autowired
    private RoomService roomService;

    @GetMapping("/availabilityCheckForm")
    public String showAvailabilityCheckForm(HttpSession session) {
        String token = (String) session.getAttribute("token");
        if(token!=null) {
        return "RoomAvailability";
    }
        return "redirect:/app/auth/login";
    }

    @PostMapping("/availableRooms")
    public String checkRoomAvailability(
            @RequestParam("stayStartDate") LocalDate stayStartDate,
            @RequestParam("stayEndDate") LocalDate stayEndDate,
            Model model) {
            List<RoomEntity> availableRooms = roomService.findAvailableRooms(stayStartDate, stayEndDate);
            model.addAttribute("stayStartDate", stayStartDate);
            model.addAttribute("stayEndDate", stayEndDate);
            model.addAttribute("availableRooms", availableRooms);
        return "availableRooms";
    }
}
