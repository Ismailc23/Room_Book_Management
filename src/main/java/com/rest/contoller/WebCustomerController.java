package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.Entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
public class WebCustomerController {

    @GetMapping("/customerCreation")
    public String showCustomerForm(Model model)
    {
        model.addAttribute("customer",new CustomerEntity());
        return "customerForm";
    }

    @PostMapping("/customerCreation")
    public String submitCustomerForm(@ModelAttribute CustomerEntity customer, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerEntity> response = restTemplate.postForEntity("http://localhost:8080/request/api/customer", customer, CustomerEntity.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            model.addAttribute("message", "Customer created successfully!");
        } else {
            model.addAttribute("message", "Failed to create customer: " + response.getBody());
        }
        return "redirect:/availabilityCheckForm";
    }


}
