package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CustomerDto;
import com.litethinking.Inventario.model.Customer;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CustomerRepository;
import com.litethinking.Inventario.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Response saveCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail(), customerDto.getAddress(), customerDto.getPhone());
        customerRepository.save(customer);
        return new Response("OK");
    }

    public Response updateCustomer(Long id, CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findById(String.valueOf(id));
        if (customer.isPresent()) {
            customer.get().setName(customerDto.getName());
            customer.get().setEmail(customerDto.getEmail());
            customer.get().setAddress(customerDto.getAddress());
            customer.get().setPhone(customerDto.getPhone());
            customerRepository.save(customer.get());
            return new Response("OK");
        } else {
            return new Response(true, "Customer not found");
        }
    }

    public Response getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(String.valueOf(id));
        if (customer.isPresent()) {
            return new Response(Collections.singletonList(customer.get()));
        } else {
            return new Response(true, "Customer not found");
        }
    }

    public Response getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return new Response(customers);
    }

    @Transactional
    public Response deleteCustomer(Long id) {
        customerRepository.deleteById(String.valueOf(id));
        return new Response("OK");
    }
}