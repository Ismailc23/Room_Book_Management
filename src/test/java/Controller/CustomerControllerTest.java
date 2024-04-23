package Controller;

import com.customer.Entity.CustomerEntity;
import com.customer.contoller.CustomerController;
import com.customer.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

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

    @Test
    public void testCreateCustomer() throws Exception {
        Date dob = parseDate("2002-02-23");
        CustomerEntity customer = new CustomerEntity(1L, "Mohammed", "Ismail", dob, "mohammedismailc76@gmail.com", "hippo123", "password");
        when(customerService.createCustomer(customer)).thenReturn(customer);
        mockMvc.perform(post("/request/api/customer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }
}
