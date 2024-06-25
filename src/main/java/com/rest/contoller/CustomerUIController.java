package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerUIController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customerform")
    public String showForm(Model model) {
        model.addAttribute("customer", new CustomerEntity());
        return "customerForm";
    }

    @PostMapping("/customeform")
    public String submitForm(CustomerEntity customer) {
        restTemplate
        customerRepository.save(customer);
        return "redirect:/roomAvailability";
    }


}
