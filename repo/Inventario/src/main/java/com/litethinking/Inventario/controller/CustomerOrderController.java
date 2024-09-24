package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CustomerOrderDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICustomerOrderService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    private final ICustomerOrderService orderServiceImpl;

    public CustomerOrderController(ICustomerOrderService orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    /**
     * Creates a new customer order.
     *
     * @param orderDto Data Transfer Object containing order information.
     * @return Response indicating the result of the order creation.
     */
    @PostMapping("/")
    public Response createOrder(@RequestBody CustomerOrderDto orderDto) {
        return orderServiceImpl.saveOrder(orderDto);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id ID of the order to retrieve.
     * @return Response containing the order details or an error message if not found.
     */
    @GetMapping("/{id}")
    public Response getOrder(@PathVariable Long id) {
        return orderServiceImpl.getOrderById(id);
    }

    /**
     * Retrieves all orders.
     *
     * @return Response containing a list of all orders.
     */
    @GetMapping
    public Response getAllOrders() {
        return orderServiceImpl.getAllOrders();
    }

    /**
     * Retrieves all orders for a specific customer.
     *
     * @param customerId ID of the customer whose orders are to be retrieved.
     * @return Response containing a list of all orders for the specified customer.
     */
    @GetMapping("/customer/{customerId}")
    public Response getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderServiceImpl.getOrdersByCustomerId(customerId);
    }

    /**
     * Updates an existing order identified by its ID.
     *
     * @param id ID of the order to update.
     * @param orderDto Data Transfer Object containing updated order information.
     * @return Response indicating the result of the update operation.
     */
    @PutMapping("/{id}")
    public Response updateOrder(@PathVariable Long id, @RequestBody CustomerOrderDto orderDto) {
        return orderServiceImpl.updateOrder(id, orderDto);
    }

    /**
     * Deletes an order by its ID. This method is marked as @Transactional to ensure that the delete operation
     * is handled within a transaction context.
     *
     * @param id ID of the order to delete.
     * @return Response indicating the result of the delete operation.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public Response deleteOrder(@PathVariable Long id) {
        return orderServiceImpl.deleteOrder(id);
    }
}
