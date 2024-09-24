package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CustomerDto;
import com.litethinking.Inventario.model.Customer;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CustomerRepository;
import com.litethinking.Inventario.service.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer_Success() {
        // Given
        CustomerDto customerDto = new CustomerDto(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        Customer customer = new Customer(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Response response = customerService.saveCustomer(customerDto);

        // Then
        assertEquals("Customer successfully created.", response.getMessage());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_Success() {
        // Given
        CustomerDto customerDto = new CustomerDto(1L, "Updated Customer", "updated@example.com", "Updated Address", "555-5556");
        Customer customer = new Customer(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));

        // When
        Response response = customerService.updateCustomer(1L, customerDto);

        // Then
        assertEquals("Customer successfully updated.", response.getMessage());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer_NotFound() {
        // Given
        CustomerDto customerDto = new CustomerDto(1L, "Updated Customer", "updated@example.com", "Updated Address", "555-5556");
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        // When
        Response response = customerService.updateCustomer(1L, customerDto);

        // Then
        assertEquals("Error: Customer not found.", response.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testGetCustomerById_Success() {
        // Given
        Customer customer = new Customer(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));

        // When
        Response response = customerService.getCustomerById(1L);

        // Then
        assertEquals(Collections.singletonList(customer), response.getData());
    }

    @Test
    void testGetCustomerById_NotFound() {
        // Given
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        // When
        Response response = customerService.getCustomerById(1L);

        // Then
        assertEquals("Error: Customer not found.", response.getMessage());
    }

    @Test
    void testDeleteCustomer_Success() {
        // Given
        Customer customer = new Customer(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));

        // When
        Response response = customerService.deleteCustomer(1L);

        // Then
        assertEquals("Customer successfully deleted.", response.getMessage());
        verify(customerRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteCustomer_NotFound() {
        // Given
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        // When
        Response response = customerService.deleteCustomer(1L);

        // Then
        assertEquals("Error: Customer not found.", response.getMessage());
        verify(customerRepository, never()).deleteById("1");
    }
}
