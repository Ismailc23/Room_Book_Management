package ControllerTest;

import com.customer.Entity.CustomerEntity;
import com.customer.contoller.CustomerController;
import com.customer.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    CustomerController customerController;
    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;


    public Date parseDate(String currentDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
        } catch (ParseException e) {
            return null;
        }
    }

    private CustomerEntity customer;
    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setCustomerId(1L);
        customer.setFirstName("Mohammed ");
        customer.setLastName("Ismail");
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        customer.setEmail("mohammedismailc76@gmai.com");
        customer.setUserName("Hippo1");
        customer.setPassword("password");
    }

    @Test
    public void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(customer)).thenReturn(customer);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/request/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                        .andExpect(status().isCreated());
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        ResponseEntity<?> responseEntity = customerController.getcustomer(customer.getCustomerId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void testGetCustomerByNoId() {
        ResponseEntity<?> responseEntity = customerController.getcustomer(null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
