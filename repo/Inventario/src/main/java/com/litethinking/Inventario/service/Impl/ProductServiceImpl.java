package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.ProductDto;
import com.litethinking.Inventario.model.*;
import com.litethinking.Inventario.repository.CategoryRepository;
import com.litethinking.Inventario.repository.CompanyRepository;
import com.litethinking.Inventario.repository.ProductRepository;
import com.litethinking.Inventario.service.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CompanyRepository companyRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Saves a new product in the database.
     *
     * @param productDto Data Transfer Object containing product information.
     * @return Response indicating the result of the product creation.
     */
    public Response saveProduct(ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findById(productDto.getCode());
        if (existingProduct.isPresent()) {
            return new Response(true, "Error: Product already exists.");
        }

        Optional<Company> companyOptional = companyRepository.findByNit(productDto.getCompanyNit());
        if (!companyOptional.isPresent()) {
            return new Response(true, "Error: Company not found for the provided NIT.");
        }

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : productDto.getCategoryIds()) {
            Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(categoryId));
            if (categoryOptional.isPresent()) {
                categories.add(categoryOptional.get());
            } else {
                return new Response(true, "Error: Category not found for ID: " + categoryId);
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
        return new Response("Product successfully created.");
    }

    /**
     * Updates an existing product identified by its code.
     *
     * @param code Unique code of the product to update.
     * @param productDto Data Transfer Object containing updated product information.
     * @return Response indicating the result of the update operation.
     */
    public Response updateProduct(String code, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(code);
        if (!productOptional.isPresent()) {
            return new Response(true, "Error: Product not found.");
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
            return new Response(true, "Error: Company not found for the provided NIT.");
        }

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : productDto.getCategoryIds()) {
            Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(categoryId));
            if (categoryOptional.isPresent()) {
                categories.add(categoryOptional.get());
            } else {
                return new Response(true, "Error: Category not found for ID: " + categoryId);
            }
        }
        existingProduct.setCategories(categories);

        productRepository.save(existingProduct);
        return new Response("Product successfully updated.");
    }

    /**
     * Retrieves all products from the database.
     *
     * @return Response containing a list of all products.
     */
    public Response getAllProducts() {
        List<Product> products = productRepository.findAllWithCategories();
        return new Response(products);
    }

    /**
     * Retrieves a product by its unique code.
     *
     * @param code Unique code of the product to retrieve.
     * @return Response containing the product details or an error message if not found.
     */
    public Response getProductByCode(String code) {
        Optional<Product> productOptional = productRepository.findByCodeWithCategories(code);
        if (productOptional.isPresent()) {
            return new Response(Collections.singletonList(productOptional.get()));
        } else {
            return new Response(true, "Error: Product not found.");
        }
    }

    /**
     * Retrieves all products associated with a specific company identified by its NIT.
     *
     * @param companyNit NIT of the company whose products are to be retrieved.
     * @return Response containing a list of products for the specified company.
     */
    public Response getProductsByCompanyNit(String companyNit) {
        List<Product> products = productRepository.findByCompanyNit(companyNit);
        if (products.isEmpty()) {
            return new Response(true, "Error: No products found for the provided NIT.");
        }
        return new Response(products);
    }

    /**
     * Deletes a product by its unique code. This method is marked as @Transactional to ensure that
     * the delete operation is handled within a transaction context.
     *
     * @param code Unique code of the product to delete.
     * @return Response indicating the result of the delete operation.
     */
    @Transactional
    public Response deleteProduct(String code) {
        productRepository.deleteByCode(code);
        return new Response("Product successfully deleted.");
    }
}
