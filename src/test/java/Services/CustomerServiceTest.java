package Services;

import com.customer.Entity.CustomerEntity;
import com.customer.Repository.CustomerRepository;
import com.customer.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setFirstName("Mohammed ");
        customer.setLastName("Ismail");
        customer.setEmail("mohammedismailc76@gmai.com");
        customer.setUserName("Hippo1");
        customer.setPassword("password");
    }

    @Test
    void testIsCustomerAbove18() {
        customer.setDateOfBirth(Date.from(LocalDate.of(2024, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        boolean result = customerService.isCustomerAbove18(customer);
        assertTrue(result, "The customer is less than 18 years old.");
    }

    @Test
    void testIsCustomerUnder18() {
        customer.setDateOfBirth(Date.from(LocalDate.of(2024, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        boolean result = customerService.isCustomerAbove18(customer);
        assertFalse(result, "The customer is above 18 years old.");
    }




}
