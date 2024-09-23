package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CompanyDto;
import com.litethinking.Inventario.model.Company;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CompanyRepository;
import com.litethinking.Inventario.service.ICompanyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Response saveCompany(CompanyDto companyDto) {
        Optional<Company> existingCompany = companyRepository.findById(companyDto.getNIT());
        if (existingCompany.isPresent()) {
            return new Response(true, "Error: La compañía con el NIT proporcionado ya existe.");
        }

        Company company = new Company(companyDto.getNIT(), companyDto.getName(), companyDto.getAddress(), companyDto.getPhone());
        companyRepository.save(company);
        return new Response("Compañía creada exitosamente.");
    }


    public Response updateCompany(String nit, CompanyDto companyDto) {
        Optional<Company> company = companyRepository.findByNit(nit);
        if (company.isPresent()) {
            company.get().setName(companyDto.getName());
            company.get().setAddress(companyDto.getAddress());
            company.get().setPhone(companyDto.getPhone());
            companyRepository.save(company.get());
            return new Response("OK");
        } else {
            return new Response(true, "Company not found");
        }
    }

    public Response getCompanyByNit(String nit) {
        Optional<Company> company = companyRepository.findByNit(nit);
        if (company.isPresent()) {
            return new Response(Collections.singletonList(company.get()));
        } else {
            return new Response(true, "Company not found");
        }
    }

    public Response getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return new Response(companies);
    }

    @Transactional
    public Response deleteCompany(String nit) {
        companyRepository.deleteByNit(nit);
        return new Response("OK");
    }
}

