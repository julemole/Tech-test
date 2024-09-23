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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImpl implements ICustomerOrderService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Response saveOrder(CustomerOrderDto orderDto) {
        // Buscar el cliente asociado al ID proporcionado
        Optional<Customer> customerOptional = customerRepository.findById(orderDto.getCustomerId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            // Buscar los productos asociados a los códigos proporcionados
            Set<Product> products = productRepository.findAllById(orderDto.getProductCodes()).stream().collect(Collectors.toSet());
            if (products.isEmpty()) {
                return new Response(true, "No se encontraron productos para los códigos proporcionados");
            }

            // Crear una nueva orden con los datos proporcionados
            CustomerOrder order = new CustomerOrder(orderDto.getOrderDate(), customer);
            order.setProducts(products);
            order.setTotal(orderDto.getTotal());

            // Guardar la orden en el repositorio
            orderRepository.save(order);
            return new Response("Orden guardada exitosamente");
        }
        return new Response(true, "Cliente no encontrado para el ID proporcionado");
    }

    public Response updateOrder(Long id, CustomerOrderDto orderDto) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(String.valueOf(id));
        if (orderOptional.isPresent()) {
            CustomerOrder existingOrder = orderOptional.get();
            existingOrder.setOrderDate(orderDto.getOrderDate());
            existingOrder.setTotal(orderDto.getTotal());

            // Actualizar el cliente de la orden si es necesario
            Optional<Customer> customerOptional = customerRepository.findById(orderDto.getCustomerId());
            if (customerOptional.isPresent()) {
                existingOrder.setCustomer(customerOptional.get());
            } else {
                return new Response(true, "Cliente no encontrado para el ID proporcionado");
            }

            // Actualizar los productos asociados
            Set<Product> products = productRepository.findAllById(orderDto.getProductCodes()).stream().collect(Collectors.toSet());
            if (products.isEmpty()) {
                return new Response(true, "No se encontraron productos para los códigos proporcionados");
            }
            existingOrder.setProducts(products);

            orderRepository.save(existingOrder);
            return new Response("Orden actualizada exitosamente");
        }
        return new Response(true, "Orden no encontrada");
    }

    public Response getOrderById(Long id) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(String.valueOf(id));
        if (orderOptional.isPresent()) {
            return new Response(Collections.singletonList(orderOptional.get()));
        }
        return new Response(true, "Orden no encontrada");
    }

    @Override
    public Response getAllOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();
        return new Response(orders);
    }

    @Override
    public Response getOrdersByCustomerId(Long customerId) {
        List<CustomerOrder> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return new Response(true, "No se encontraron órdenes para el ID del cliente proporcionado");
        }
        return new Response(orders);
    }

    @Override
    public Response deleteOrder(Long id) {
        Optional<CustomerOrder> orderOptional = orderRepository.findById(String.valueOf(id));
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            return new Response("Orden eliminada exitosamente");
        }
        return new Response(true, "Orden no encontrada");
    }
}
