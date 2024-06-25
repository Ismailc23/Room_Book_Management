package com.rest.contoller;

import com.rest.Entity.BookingEntity;
import com.rest.Repository.BookingRepository;
import com.rest.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CheckingAvailabilityUI {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/roomAvailability")
    public String viewRoomAvailabilityPage(Model model) {
        model.addAttribute("booking",new BookingEntity());
        return "RoomAvailability";
    }

//    @PostMapping("/roomAvailability")
//    public String roomAvailable(BookingEntity booking)
//    {
//        bookingRepository.save(booking);
//        return "redirect:/user/rooms";
//    }
}
