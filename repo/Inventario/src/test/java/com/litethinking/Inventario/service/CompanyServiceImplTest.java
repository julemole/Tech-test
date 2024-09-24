package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CompanyDto;
import com.litethinking.Inventario.model.Company;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CompanyRepository;
import com.litethinking.Inventario.service.Impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCompany_Success() {
        // Given
        CompanyDto companyDto = new CompanyDto("123456789", "Company 1", "Address 1", "555-5555");
        when(companyRepository.findById(companyDto.getNIT())).thenReturn(Optional.empty());

        // When
        Response response = companyService.saveCompany(companyDto);

        // Then
        assertEquals("Company successfully created.", response.getMessage());
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void testSaveCompany_CompanyAlreadyExists() {
        // Given
        CompanyDto companyDto = new CompanyDto("123456789", "Company 1", "Address 1", "555-5555");
        when(companyRepository.findById(companyDto.getNIT())).thenReturn(Optional.of(new Company()));

        // When
        Response response = companyService.saveCompany(companyDto);

        // Then
        assertEquals("Error: Company with the provided NIT already exists.", response.getMessage());
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void testGetCompanyByNit_Success() {
        // Given
        String nit = "123456789";
        Company company = new Company(nit, "Company 1", "Address 1", "555-5555");
        when(companyRepository.findByNit(nit)).thenReturn(Optional.of(company));

        // When
        Response response = companyService.getCompanyByNit(nit);

        // Then
        assertEquals(Collections.singletonList(company), response.getData());
    }

    @Test
    void testGetCompanyByNit_NotFound() {
        // Given
        String nit = "123456789";
        when(companyRepository.findByNit(nit)).thenReturn(Optional.empty());

        // When
        Response response = companyService.getCompanyByNit(nit);

        // Then
        assertEquals("Error: Company not found.", response.getMessage());
    }

    @Test
    void testDeleteCompany_Success() {
        // Given
        String nit = "123456789";
        Company company = new Company(nit, "Company 1", "Address 1", "555-5555");
        when(companyRepository.findByNit(nit)).thenReturn(Optional.of(company));

        // When
        Response response = companyService.deleteCompany(nit);

        // Then
        assertEquals("Company successfully deleted.", response.getMessage());
        verify(companyRepository, times(1)).deleteByNit(nit);
    }

    @Test
    void testDeleteCompany_NotFound() {
        // Given
        String nit = "123456789";
        when(companyRepository.findByNit(nit)).thenReturn(Optional.empty());

        // When
        Response response = companyService.deleteCompany(nit);

        // Then
        assertEquals("Error: Company not found.", response.getMessage());
        verify(companyRepository, never()).deleteByNit(nit);
    }
}
