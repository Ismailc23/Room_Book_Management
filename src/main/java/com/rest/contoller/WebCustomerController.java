package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/updateCustomerForm")
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        String url = "http://localhost:8080/request/api/customer/" + id;
        CustomerEntity customer = restTemplate.getForObject(url, CustomerEntity.class);
        model.addAttribute("customer", customer);
        return "updateCustomer";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(CustomerEntity customer, Model model) {
        String url = "http://localhost:8080/request/api/customer/" + customer.getCustomerId();
        try {
            restTemplate.put(url, customer);
            model.addAttribute("message", "Customer updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating customer: " + e.getMessage());
        }
        return "redirect:/customerDetails/"+customer.getCustomerId();
    }

    @GetMapping("/deleteCustomerForm")
    public String deleteCustomer(@RequestParam("id") Long id, Model model) {
        String url = "http://localhost:8080/request/api/customer/" + id;
        CustomerEntity customer = restTemplate.getForObject(url, CustomerEntity.class);
        model.addAttribute("customer", customer);
        return "DeleteCustomer";
    }

    @PostMapping("/deleteCustomer")
    public String deleteCustomer(CustomerEntity customer, Model model) {
        String url = "http://localhost:8080/request/api/customer/" + customer.getCustomerId();
        try {
            restTemplate.delete(url);
            model.addAttribute("message", "Customer deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/customerForm";
    }
}
