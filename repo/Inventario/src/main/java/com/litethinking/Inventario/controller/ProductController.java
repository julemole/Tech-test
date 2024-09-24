package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.ProductDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.IProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productServiceImpl;

    public ProductController(IProductService productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    /**
     * Creates a new product.
     *
     * @param productDto Data Transfer Object containing product information.
     * @return Response indicating the result of the product creation.
     */
    @PostMapping("/")
    public Response createProduct(@RequestBody ProductDto productDto) {
        return productServiceImpl.saveProduct(productDto);
    }

    /**
     * Retrieves a product by its code.
     *
     * @param code Unique code of the product to retrieve.
     * @return Response containing the product details or an error message if not found.
     */
    @GetMapping("/{code}")
    public Response getProduct(@PathVariable String code) {
        return productServiceImpl.getProductByCode(code);
    }

    /**
     * Retrieves all products.
     *
     * @return Response containing a list of all products.
     */
    @GetMapping
    public Response getAllProducts() {
        return productServiceImpl.getAllProducts();
    }

    /**
     * Updates an existing product identified by its code.
     *
     * @param code Unique code of the product to update.
     * @param productDto Data Transfer Object containing updated product information.
     * @return Response indicating the result of the update operation.
     */
    @PutMapping("/{code}")
    public Response updateProduct(@PathVariable String code, @RequestBody ProductDto productDto) {
        return productServiceImpl.updateProduct(code, productDto);
    }

    /**
     * Deletes a product by its code.
     *
     * @param code Unique code of the product to delete.
     * @return Response indicating the result of the delete operation.
     */
    @DeleteMapping("/{code}")
    public Response deleteProduct(@PathVariable String code) {
        return productServiceImpl.deleteProduct(code);
    }

    /**
     * Retrieves all products associated with a specific company identified by its NIT.
     *
     * @param nit NIT of the company whose products are to be retrieved.
     * @return Response containing a list of all products for the specified company.
     */
    @GetMapping("/company/{nit}")
    public Response getProductsByCompanyNit(@PathVariable String nit) {
        return productServiceImpl.getProductsByCompanyNit(nit);
    }
}
