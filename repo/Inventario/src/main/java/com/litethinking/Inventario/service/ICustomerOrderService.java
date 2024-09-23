package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CustomerOrderDto;
import com.litethinking.Inventario.model.Response;

public interface ICustomerOrderService {
    public Response saveOrder(CustomerOrderDto orderDto);
    public Response updateOrder(Long id, CustomerOrderDto orderDto);
    public Response getOrderById(Long id);
    public Response getAllOrders();
    public Response getOrdersByCustomerId(Long customerId);
    public Response deleteOrder(Long id);
}
