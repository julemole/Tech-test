package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.ProductDto;
import com.litethinking.Inventario.model.Response;

public interface IProductService {
    public Response saveProduct(ProductDto productDto);
    public Response updateProduct(String code, ProductDto productDto);

    public Response getProductByCode(String code);

    public Response getAllProducts();
    public Response getProductsByCompanyNit(String companyNit);

    public Response deleteProduct(String code);
}
