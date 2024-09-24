package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CompanyDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICompanyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final ICompanyService companyServiceImpl;

    public CompanyController(ICompanyService companyServiceImpl) {
        this.companyServiceImpl = companyServiceImpl;
    }

    /**
     * Creates a new company.
     *
     * @param companyDto Data Transfer Object containing company information.
     * @return Response indicating the result of the company creation.
     */
    @PostMapping("/")
    public Response createCompany(@RequestBody CompanyDto companyDto) {
        Response result = companyServiceImpl.saveCompany(companyDto);
        return result;
    }

    /**
     * Updates an existing company identified by its NIT.
     *
     * @param nit NIT of the company to update.
     * @param companyDto Data Transfer Object containing updated company information.
     * @return Response indicating the result of the update operation.
     */
    @PutMapping("/{nit}")
    public Response updateCompany(@PathVariable String nit, @RequestBody CompanyDto companyDto) {
        Response result = companyServiceImpl.updateCompany(nit, companyDto);
        return result;
    }

    /**
     * Retrieves a company by its NIT.
     *
     * @param nit NIT of the company to retrieve.
     * @return Response containing the company details or an error message if not found.
     */
    @GetMapping("/{nit}")
    public Response getCompany(@PathVariable String nit) {
        Response result = companyServiceImpl.getCompanyByNit(nit);
        return result;
    }

    /**
     * Retrieves all companies.
     *
     * @return Response containing a list of all companies.
     */
    @GetMapping
    public Response getAllCompanies() {
        Response result = companyServiceImpl.getAllCompanies();
        return result;
    }

    /**
     * Deletes a company by its NIT.
     *
     * @param nit NIT of the company to delete.
     * @return Response indicating the result of the delete operation.
     */
    @DeleteMapping("/{nit}")
    public Response deleteCompany(@PathVariable String nit) {
        Response result = companyServiceImpl.deleteCompany(nit);
        return result;
    }
}
