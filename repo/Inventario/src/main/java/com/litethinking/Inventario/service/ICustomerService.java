package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CustomerDto;
import com.litethinking.Inventario.model.Response;
public interface ICustomerService {
    public Response saveCustomer(CustomerDto customerDto);
    public Response updateCustomer(Long id, CustomerDto customerDto);

    public Response getCustomerById(Long id);

    public Response getAllCustomers();

    public Response deleteCustomer(Long id);
}
