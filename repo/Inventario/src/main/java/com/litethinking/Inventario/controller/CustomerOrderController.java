package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CustomerOrderDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICustomerOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    @Autowired
    private ICustomerOrderService orderServiceImpl;

    @PostMapping("/")
    public Response createOrder(@RequestBody CustomerOrderDto orderDto) {
        return orderServiceImpl.saveOrder(orderDto);
    }

    @GetMapping("/{id}")
    public Response getOrder(@PathVariable Long id) {
        return orderServiceImpl.getOrderById(id);
    }

    @GetMapping
    public Response getAllOrders() {
        return orderServiceImpl.getAllOrders();
    }

    @GetMapping("/customer/{customerId}")
    public Response getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderServiceImpl.getOrdersByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public Response updateOrder(@PathVariable Long id, @RequestBody CustomerOrderDto orderDto) {
        return orderServiceImpl.updateOrder(id, orderDto);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public Response deleteOrder(@PathVariable Long id) {
        return orderServiceImpl.deleteOrder(id);
    }
}
