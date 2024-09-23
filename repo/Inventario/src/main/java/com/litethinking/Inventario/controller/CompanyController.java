package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CompanyDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private ICompanyService companyServiceImpl;

    @PostMapping("/")
    public Response createCompany(@RequestBody CompanyDto companyDto) {
        Response result = companyServiceImpl.saveCompany(companyDto);
        return result;
    }

    @PutMapping("/{nit}")
    public Response updateCompany(@PathVariable String nit, @RequestBody CompanyDto companyDto) {
        Response result = companyServiceImpl.updateCompany(nit, companyDto);
        return result;
    }

    @GetMapping("/{nit}")
    public Response getCompany(@PathVariable String nit) {
        Response result = companyServiceImpl.getCompanyByNit(nit);
        return result;
    }

    @GetMapping
    public Response getAllCompanies() {
        Response result = companyServiceImpl.getAllCompanies();
        return result;
    }

    @DeleteMapping("/{nit}")
    public Response deleteCompany(@PathVariable String nit) {
        Response result = companyServiceImpl.deleteCompany(nit);
        return result;
    }
}

