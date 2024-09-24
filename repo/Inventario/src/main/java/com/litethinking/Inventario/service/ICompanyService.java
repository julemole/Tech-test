package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CompanyDto;
import com.litethinking.Inventario.model.Response;

public interface ICompanyService {
    public Response saveCompany(CompanyDto companyDto);
    public Response updateCompany(String nit, CompanyDto companyDto);

    public Response getCompanyByNit(String nit);

    public Response getAllCompanies();

    public Response deleteCompany(String nit);
}
