package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CustomerDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerServiceImpl;

    @PostMapping("/")
    public Response createCustomer(@RequestBody CustomerDto customerDto) {
        Response result = customerServiceImpl.saveCustomer(customerDto);
        return result;
    }

    @PutMapping("/{id}")
    public Response updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Response result = customerServiceImpl.updateCustomer(id, customerDto);
        return result;
    }

    @GetMapping("/{id}")
    public Response getCustomerById(@PathVariable Long id) {
        Response result = customerServiceImpl.getCustomerById(id);
        return result;
    }

    @GetMapping
    public Response getAllCustomers() {
        Response result = customerServiceImpl.getAllCustomers();
        return result;
    }

    @DeleteMapping("/{id}")
    public Response deleteCustomer(@PathVariable Long id) {
        Response result = customerServiceImpl.deleteCustomer(id);
        return result;
    }
}