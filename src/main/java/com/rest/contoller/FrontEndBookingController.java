package com.rest.contoller;

import com.rest.Entity.BookingEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Controller
public class FrontEndBookingController {

    @PostMapping("/makeBooking")
    public String makeBooking(
            @RequestParam("roomNumber") Long roomNumber,
            @RequestParam("stayStartDate") LocalDate stayStartDate,
            @RequestParam("stayEndDate") LocalDate stayEndDate,
            HttpSession session,
            Model model) {
        Long customerId = (Long) session.getAttribute("Id");
        if (customerId == null) {
            model.addAttribute("error", "Customer not logged in");
            return "errorPage"; // Return an error page if customer is not logged in
        }

        BookingEntity booking = new BookingEntity();
        booking.setStayStartDate(stayStartDate);
        booking.setStayEndDate(stayEndDate);

        String bookingApiUrl = String.format("http://localhost:8080/api/customers/%d/%d", customerId, roomNumber);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<BookingEntity> response = restTemplate.postForEntity(bookingApiUrl, booking, BookingEntity.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                BookingEntity createdBooking = response.getBody();
                model.addAttribute("booking", createdBooking);
                return "bookingConfirmation"; // Return the booking confirmation page
            } else {
                model.addAttribute("error", "Booking failed");
                return "errorPage"; // Return an error page if booking failed
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            model.addAttribute("error", e.getResponseBodyAsString());
            return "errorPage"; // Return an error page if there was an exception
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred");
            return "errorPage"; // Return an error page for unexpected errors
        }
    }
}
