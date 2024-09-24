package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.ProductDto;
import com.litethinking.Inventario.model.Category;
import com.litethinking.Inventario.model.Company;
import com.litethinking.Inventario.model.Product;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CategoryRepository;
import com.litethinking.Inventario.repository.CompanyRepository;
import com.litethinking.Inventario.repository.ProductRepository;
import com.litethinking.Inventario.service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct_Success() {
        // Given
        ProductDto productDto = new ProductDto();
        productDto.setCode("P001");
        productDto.setName("Product 1");
        productDto.setDescription("Description 1");
        productDto.setPrice(100.0);
        productDto.setCompanyNit("123456789");
        productDto.setCategoryIds(Set.of(1L, 2L));
        productDto.setIsInInventory(true);

        Company company = new Company("123456789", "Company 1", "Address 1", "555-5555");
        when(companyRepository.findByNit(productDto.getCompanyNit())).thenReturn(Optional.of(company));

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        when(categoryRepository.findById("1")).thenReturn(Optional.of(category1));
        when(categoryRepository.findById("2")).thenReturn(Optional.of(category2));
        when(productRepository.findById(productDto.getCode())).thenReturn(Optional.empty());

        // When
        Response response = productService.saveProduct(productDto);

        // Then
        assertEquals("Product successfully created.", response.getMessage());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_ProductAlreadyExists() {
        // Given
        ProductDto productDto = new ProductDto();
        productDto.setCode("P001");

        Product existingProduct = new Product();
        when(productRepository.findById(productDto.getCode())).thenReturn(Optional.of(existingProduct));

        // When
        Response response = productService.saveProduct(productDto);

        // Then
        assertEquals("Error: Product already exists.", response.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }
}
