package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CustomerDto;
import com.litethinking.Inventario.model.Customer;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CustomerRepository;
import com.litethinking.Inventario.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Saves a new customer in the database.
     *
     * @param customerDto Data Transfer Object containing customer information.
     * @return Response indicating the result of the customer creation.
     */
    public Response saveCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail(), customerDto.getAddress(), customerDto.getPhone());
        customerRepository.save(customer);
        return new Response("Customer successfully created.");
    }

    /**
     * Updates an existing customer identified by its ID.
     *
     * @param id ID of the customer to update.
     * @param customerDto Data Transfer Object containing updated customer information.
     * @return Response indicating the result of the update operation.
     */
    public Response updateCustomer(Long id, CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findById(String.valueOf(id));
        if (customer.isPresent()) {
            customer.get().setName(customerDto.getName());
            customer.get().setEmail(customerDto.getEmail());
            customer.get().setAddress(customerDto.getAddress());
            customer.get().setPhone(customerDto.getPhone());
            customerRepository.save(customer.get());
            return new Response("Customer successfully updated.");
        } else {
            return new Response(true, "Error: Customer not found.");
        }
    }

    /**
     * Retrieves a customer by its ID.
     *
     * @param id ID of the customer to retrieve.
     * @return Response containing the customer details or an error message if not found.
     */
    public Response getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(String.valueOf(id));
        if (customer.isPresent()) {
            return new Response(Collections.singletonList(customer.get()));
        } else {
            return new Response(true, "Error: Customer not found.");
        }
    }

    /**
     * Retrieves all customers.
     *
     * @return Response containing a list of all customers.
     */
    public Response getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return new Response(customers);
    }

    /**
     * Deletes a customer by its ID. This method is marked as @Transactional to ensure that
     * the delete operation is handled within a transaction context.
     *
     * @param id ID of the customer to delete.
     * @return Response indicating the result of the delete operation.
     */
    @Transactional
    public Response deleteCustomer(Long id) {
        customerRepository.deleteById(String.valueOf(id));
        return new Response("Customer successfully deleted.");
    }
}
