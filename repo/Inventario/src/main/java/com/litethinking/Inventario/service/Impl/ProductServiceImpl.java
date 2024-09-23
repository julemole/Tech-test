package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.ProductDto;
import com.litethinking.Inventario.model.*;
import com.litethinking.Inventario.repository.CategoryRepository;
import com.litethinking.Inventario.repository.CompanyRepository;
import com.litethinking.Inventario.repository.ProductRepository;
import com.litethinking.Inventario.service.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Response saveProduct(ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findById(productDto.getCode());
        if (existingProduct.isPresent()) {
            return new Response(true, "El producto ya existe");
        }

        Optional<Company> companyOptional = companyRepository.findByNit(productDto.getCompanyNit());
        if (!companyOptional.isPresent()) {
            return new Response(true, "Compañía no encontrada para el NIT proporcionado");
        }

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : productDto.getCategoryIds()) {
            Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(categoryId));
            if (categoryOptional.isPresent()) {
                categories.add(categoryOptional.get());
            } else {
                return new Response(true, "Categoría no encontrada para el ID: " + categoryId);
            }
        }

        Product product = new Product(
                productDto.getCode(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                companyOptional.get(),
                categories,
                productDto.getIsInInventory()
        );

        productRepository.save(product);
        return new Response("Producto creado correctamente");
    }

    public Response updateProduct(String code, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(code);
        if (!productOptional.isPresent()) {
            return new Response(true, "Producto no encontrado");
        }

        Product existingProduct = productOptional.get();
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setIsInInventory(productDto.getIsInInventory());

        Optional<Company> companyOptional = companyRepository.findByNit(productDto.getCompanyNit());
        if (companyOptional.isPresent()) {
            existingProduct.setCompany(companyOptional.get());
        } else {
            return new Response(true, "Compañía no encontrada para el NIT proporcionado");
        }

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : productDto.getCategoryIds()) {
            Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(categoryId));
            if (categoryOptional.isPresent()) {
                categories.add(categoryOptional.get());
            } else {
                return new Response(true, "Categoría no encontrada para el ID: " + categoryId);
            }
        }
        existingProduct.setCategories(categories);

        productRepository.save(existingProduct);
        return new Response("Producto actualizado exitosamente");
    }

    public Response getAllProducts() {
        List<Product> products = productRepository.findAllWithCategories();
        return new Response(products);
    }

    public Response getProductByCode(String code) {
        Optional<Product> productOptional = productRepository.findByCodeWithCategories(code); // Usar la consulta personalizada
        if (productOptional.isPresent()) {
            return new Response(Collections.singletonList(productOptional.get()));
        } else {
            return new Response(true, "Product not found.");
        }
    }

    public Response getProductsByCompanyNit(String companyNit) {
        List<Product> products = productRepository.findByCompanyNit(companyNit);
        if (products.isEmpty()) {
            return new Response("No se encontraron productos para el NIT proporcionado");
        }
        return new Response("Productos encontrados exitosamente");
    }

    @Transactional
    public Response deleteProduct(String code) {
        productRepository.deleteByCode(code);
        return new Response("OK");
    }
}
