package ServicesTest;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class
CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setCustomerId(1l);
        customer.setFirstName("Mohammed ");
        customer.setLastName("Ismail");
        customer.setEmail("mohammedismailc76@gmai.com");
        customer.setUserName("Hippo1");
        customer.setPassword("password");
    }

    @Test
    void testIsCustomerAbove18() {
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        boolean result = customerService.isCustomerAbove18(customer);
        assertTrue(result);
    }

    @Test
    void testIsCustomerUnder18() {
        customer.setDateOfBirth(Date.from(LocalDate.of(2024, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        boolean result = customerService.isCustomerAbove18(customer);
        assertFalse(result);
    }

    @Test
    void testCreateCustomerabove18(){
        customer.setDateOfBirth(Date.from(LocalDate.of(2002, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(customerRepository.save(customer)).thenReturn(customer);
        CustomerEntity createdCustomer = customerService.createCustomer(customer);
        assertEquals(customer, createdCustomer);
    }

    @Test
    void testCreateCustomerbelow18(){
        customer.setDateOfBirth(Date.from(LocalDate.of(2010, 2, 23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        CustomerEntity createdCustomer = customerService.createCustomer(customer);
        assertEquals(null, createdCustomer);
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Optional<CustomerEntity> retrievedCustomer = customerService.getCustomerById(1L);
        assertTrue(retrievedCustomer.isPresent());
        assertEquals(customer, retrievedCustomer.get());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<CustomerEntity> retrievedCustomer = customerService.getCustomerById(2L);
        assertFalse(retrievedCustomer.isPresent());
    }

}
