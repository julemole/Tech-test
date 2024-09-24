package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CompanyDto;
import com.litethinking.Inventario.model.Company;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CompanyRepository;
import com.litethinking.Inventario.service.ICompanyService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Saves a new company in the database.
     *
     * @param companyDto Data Transfer Object containing company information.
     * @return Response indicating the result of the company creation.
     */
    public Response saveCompany(CompanyDto companyDto) {
        Optional<Company> existingCompany = companyRepository.findById(companyDto.getNIT());
        if (existingCompany.isPresent()) {
            return new Response(true, "Error: Company with the provided NIT already exists.");
        }

        Company company = new Company(companyDto.getNIT(), companyDto.getName(), companyDto.getAddress(), companyDto.getPhone());
        companyRepository.save(company);
        return new Response("Company successfully created.");
    }

    /**
     * Updates an existing company identified by its NIT.
     *
     * @param nit NIT of the company to update.
     * @param companyDto Data Transfer Object containing updated company information.
     * @return Response indicating the result of the update operation.
     */
    public Response updateCompany(String nit, CompanyDto companyDto) {
        Optional<Company> company = companyRepository.findByNit(nit);
        if (company.isPresent()) {
            company.get().setName(companyDto.getName());
            company.get().setAddress(companyDto.getAddress());
            company.get().setPhone(companyDto.getPhone());
            companyRepository.save(company.get());
            return new Response("Company successfully updated.");
        } else {
            return new Response(true, "Error: Company not found.");
        }
    }

    /**
     * Retrieves a company by its NIT.
     *
     * @param nit NIT of the company to retrieve.
     * @return Response containing the company details or an error message if not found.
     */
    public Response getCompanyByNit(String nit) {
        Optional<Company> company = companyRepository.findByNit(nit);
        if (company.isPresent()) {
            return new Response(Collections.singletonList(company.get()));
        } else {
            return new Response(true, "Error: Company not found.");
        }
    }

    /**
     * Retrieves all companies.
     *
     * @return Response containing a list of all companies.
     */
    public Response getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return new Response(companies);
    }

    /**
     * Deletes a company by its NIT. This method is marked as @Transactional to ensure that
     * the delete operation is handled within a transaction context.
     *
     * @param nit NIT of the company to delete.
     * @return Response indicating the result of the delete operation.
     */
    @Transactional
    public Response deleteCompany(String nit) {
        companyRepository.deleteByNit(nit);
        return new Response("Company successfully deleted.");
    }
}
