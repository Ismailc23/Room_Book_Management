package com.rest.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckingAvailabilityUI {

    @GetMapping("/roomAvailability")
    public String viewRoomAvailabilityPage() {

        return "RoomAvailability";
    }
}
