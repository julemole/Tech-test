package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CustomerOrderDto;
import com.litethinking.Inventario.model.*;
import com.litethinking.Inventario.repository.CustomerOrderRepository;
import com.litethinking.Inventario.repository.CustomerRepository;
import com.litethinking.Inventario.repository.ProductRepository;
import com.litethinking.Inventario.service.Impl.CustomerOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerOrderServiceImplTest {

    @Mock
    private CustomerOrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CustomerOrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder_Success() {
        // Given
        CustomerOrderDto orderDto = new CustomerOrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setOrderDate(new Date());
        orderDto.setProductCodes(Set.of(1L, 2L));
        orderDto.setTotal(100.0);

        Customer customer = new Customer(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        when(customerRepository.findById(orderDto.getCustomerId())).thenReturn(Optional.of(customer));

        Product product1 = new Product("1", "Product 1", "Description 1", 50.0, null, new HashSet<>(), true);
        Product product2 = new Product("2", "Product 2", "Description 2", 50.0, null, new HashSet<>(), true);

        when(productRepository.findAllById(orderDto.getProductCodes())).thenReturn(Set.of(product1, product2));

        // When
        Response response = orderService.saveOrder(orderDto);

        // Then
        assertEquals("Order successfully saved.", response.getMessage());
        verify(orderRepository, times(1)).save(any(CustomerOrder.class));
    }

    @Test
    void testSaveOrder_CustomerNotFound() {
        // Given
        CustomerOrderDto orderDto = new CustomerOrderDto();
        orderDto.setCustomerId(1L);

        when(customerRepository.findById(orderDto.getCustomerId())).thenReturn(Optional.empty());

        // When
        Response response = orderService.saveOrder(orderDto);

        // Then
        assertEquals("Error: Customer not found for the provided ID.", response.getMessage());
        verify(orderRepository, never()).save(any(CustomerOrder.class));
    }

    @Test
    void testSaveOrder_ProductsNotFound() {
        // Given
        CustomerOrderDto orderDto = new CustomerOrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setProductCodes(Set.of("1", "2"));

        Customer customer = new Customer(1L, "Customer 1", "customer1@example.com", "Address 1", "555-5555");
        when(customerRepository.findById(orderDto.getCustomerId())).thenReturn(Optional.of(customer));

        when(productRepository.findAllById(orderDto.getProductCodes())).thenReturn(Collections.emptySet());

        // When
        Response response = orderService.saveOrder(orderDto);

        // Then
        assertEquals("Error: No products found for the provided codes.", response.getMessage());
        verify(orderRepository, never()).save(any(CustomerOrder.class));
    }


    @Test
    void testGetOrderById_Success() {
        // Given
        CustomerOrder order = new CustomerOrder();
        order.setId(1L);
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));

        // When
        Response response = orderService.getOrderById(1L);

        // Then
        assertEquals(Collections.singletonList(order), response.getData());
    }

    @Test
    void testGetOrderById_NotFound() {
        // Given
        when(orderRepository.findById("1")).thenReturn(Optional.empty());

        // When
        Response response = orderService.getOrderById(1L);

        // Then
        assertEquals("Error: Order not found.", response.getMessage());
    }

    @Test
    void testDeleteOrder_Success() {
        // Given
        CustomerOrder order = new CustomerOrder();
        order.setId(1L);
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));

        // When
        Response response = orderService.deleteOrder(1L);

        // Then
        assertEquals("Order successfully deleted.", response.getMessage());
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void testDeleteOrder_NotFound() {
        // Given
        when(orderRepository.findById("1")).thenReturn(Optional.empty());

        // When
        Response response = orderService.deleteOrder(1L);

        // Then
        assertEquals("Error: Order not found.", response.getMessage());
        verify(orderRepository, never()).delete(any(CustomerOrder.class));
    }
}
