package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class WebCustomerController {


    @Autowired
    private CustomerService customerService;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/customerForm")
    public String showCustomerForm(Model model)
    {
        model.addAttribute("customer",new CustomerEntity());
        return "customerForm";
    }

    @PostMapping("/customerCreation")
    public String submitCustomerForm(CustomerEntity customer,Model model) {
        ResponseEntity<CustomerEntity> response = restTemplate.postForEntity("http://localhost:8080/request/api/customer", customer, CustomerEntity.class);
        CustomerEntity createdCustomer = response.getBody();
        return "redirect:/customerDetails/"+createdCustomer.getCustomerId();
    }

    @GetMapping("/customerlist")
    public String showAllCustomers(Model model) {
        List<CustomerEntity> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "CustomersList";
    }

    @GetMapping("/customerDetails/{id}")
    public String getCustomerDetails(@PathVariable Long id,Model model){
        CustomerEntity customer = restTemplate.getForObject("http://localhost:8080/request/api/customer/"+id, CustomerEntity.class);
        model.addAttribute("customer",customer);
        return "customerDetails";
    }
}
