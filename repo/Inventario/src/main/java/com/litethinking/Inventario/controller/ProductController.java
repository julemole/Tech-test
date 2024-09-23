package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.ProductDto;
import com.litethinking.Inventario.model.Product;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.IProductService;
import com.litethinking.Inventario.service.Impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productServiceImpl;

    @PostMapping("/")
    public Response createProduct(@RequestBody ProductDto productDto) {
        return productServiceImpl.saveProduct(productDto);
    }

    @GetMapping("/{code}")
    public Response getProduct(@PathVariable String code) {
        return productServiceImpl.getProductByCode(code);
    }

    @GetMapping
    public Response getAllProducts() {
        return productServiceImpl.getAllProducts();
    }

    @PutMapping("/{code}")
    public Response updateProduct(@PathVariable String code, @RequestBody ProductDto productDto) {
        return productServiceImpl.updateProduct(code, productDto);
    }

    @DeleteMapping("/{code}")
    public Response deleteProduct(@PathVariable String code) {
        return productServiceImpl.deleteProduct(code);
    }

    @GetMapping("/company/{nit}")
    public Response getProductsByCompanyNit(@PathVariable String nit) {
        return productServiceImpl.getProductsByCompanyNit(nit);
    }
}