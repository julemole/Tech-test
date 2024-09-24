package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CustomerDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final ICustomerService customerServiceImpl;

    public CustomerController(ICustomerService customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    /**
     * Creates a new customer.
     *
     * @param customerDto Data Transfer Object containing customer information.
     * @return Response indicating the result of the customer creation.
     */
    @PostMapping("/")
    public Response createCustomer(@RequestBody CustomerDto customerDto) {
        Response result = customerServiceImpl.saveCustomer(customerDto);
        return result;
    }

    /**
     * Updates an existing customer identified by its ID.
     *
     * @param id ID of the customer to update.
     * @param customerDto Data Transfer Object containing updated customer information.
     * @return Response indicating the result of the update operation.
     */
    @PutMapping("/{id}")
    public Response updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Response result = customerServiceImpl.updateCustomer(id, customerDto);
        return result;
    }

    /**
     * Retrieves a customer by its ID.
     *
     * @param id ID of the customer to retrieve.
     * @return Response containing the customer details or an error message if not found.
     */
    @GetMapping("/{id}")
    public Response getCustomerById(@PathVariable Long id) {
        Response result = customerServiceImpl.getCustomerById(id);
        return result;
    }

    /**
     * Retrieves all customers.
     *
     * @return Response containing a list of all customers.
     */
    @GetMapping
    public Response getAllCustomers() {
        Response result = customerServiceImpl.getAllCustomers();
        return result;
    }

    /**
     * Deletes a customer by its ID.
     *
     * @param id ID of the customer to delete.
     * @return Response indicating the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public Response deleteCustomer(@PathVariable Long id) {
        Response result = customerServiceImpl.deleteCustomer(id);
        return result;
    }
}
