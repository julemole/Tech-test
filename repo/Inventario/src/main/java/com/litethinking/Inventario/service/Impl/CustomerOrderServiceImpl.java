package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CustomerOrderDto;
import com.litethinking.Inventario.model.Customer;
import com.litethinking.Inventario.model.CustomerOrder;
import com.litethinking.Inventario.model.Product;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CustomerOrderRepository;
import com.litethinking.Inventario.repository.CustomerRepository;
import com.litethinking.Inventario.repository.ProductRepository;
import com.litethinking.Inventario.service.ICustomerOrderService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImpl implements ICustomerOrderService {

    private final CustomerOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    /**
     * Saves a new customer order in the database.
     *
     * @param orderDto Data Transfer Object containing order information.
     * @return Response indicating the result of the order creation.
     */
    public Response saveOrder(CustomerOrderDto orderDto) {
        // Find the customer associated with the provided ID
        Optional<Customer> customerOptional = customerRepository.findById(orderDto.getCustomerId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            // Find the products associated with the provided codes
            Set<Product> products = productRepository.findAllById(orderDto.getProductCodes()).stream().collect(Collectors.toSet());
            if (products.isEmpty()) {
                return new Response(true, "Error: No products found for the provided codes.");
            }

            // Create a new order with the provided data
            CustomerOrder order = new CustomerOrder(orderDto.getOrderDate(), customer);
            order.setProducts(products);
            order.setTotal(orderDto.getTotal());

            // Save the order in the repository
            orderRepository.save(order);
            return new Response("Order successfully saved.");
        }
        return new Response(true, "Error: Customer not found for the provided ID.");
    }

    /**
     * Updates an existing customer order identified by its ID.
     *
     * @param id ID of the order to update.
     * @param orderDto Data Transfer Object containing updated order information.
     * @return Response indicating the result of the update operation.
     */
    public Response updateOrder(Long id, CustomerOrderDto orderDto) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(String.valueOf(id));
        if (orderOptional.isPresent()) {
            CustomerOrder existingOrder = orderOptional.get();
            existingOrder.setOrderDate(orderDto.getOrderDate());
            existingOrder.setTotal(orderDto.getTotal());

            // Update the customer associated with the order if necessary
            Optional<Customer> customerOptional = customerRepository.findById(orderDto.getCustomerId());
            if (customerOptional.isPresent()) {
                existingOrder.setCustomer(customerOptional.get());
            } else {
                return new Response(true, "Error: Customer not found for the provided ID.");
            }

            // Update the products associated with the order
            Set<Product> products = productRepository.findAllById(orderDto.getProductCodes()).stream().collect(Collectors.toSet());
            if (products.isEmpty()) {
                return new Response(true, "Error: No products found for the provided codes.");
            }
            existingOrder.setProducts(products);

            orderRepository.save(existingOrder);
            return new Response("Order successfully updated.");
        }
        return new Response(true, "Error: Order not found.");
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id ID of the order to retrieve.
     * @return Response containing the order details or an error message if not found.
     */
    public Response getOrderById(Long id) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(String.valueOf(id));
        if (orderOptional.isPresent()) {
            return new Response(Collections.singletonList(orderOptional.get()));
        }
        return new Response(true, "Error: Order not found.");
    }

    /**
     * Retrieves all customer orders.
     *
     * @return Response containing a list of all orders.
     */
    @Override
    public Response getAllOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();
        return new Response(orders);
    }

    /**
     * Retrieves all orders associated with a specific customer.
     *
     * @param customerId ID of the customer whose orders are to be retrieved.
     * @return Response containing a list of orders for the specified customer.
     */
    @Override
    public Response getOrdersByCustomerId(Long customerId) {
        List<CustomerOrder> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return new Response(true, "Error: No orders found for the provided customer ID.");
        }
        return new Response(orders);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id ID of the order to delete.
     * @return Response indicating the result of the delete operation.
     */
    @Override
    public Response deleteOrder(Long id) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(String.valueOf(id));
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            return new Response("Order successfully deleted.");
        }
        return new Response(true, "Error: Order not found.");
    }
}
