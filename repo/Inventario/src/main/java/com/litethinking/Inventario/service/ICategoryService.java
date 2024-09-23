package com.litethinking.Inventario.service;

import com.litethinking.Inventario.dto.CategoryDto;
import com.litethinking.Inventario.model.Response;

public interface ICategoryService {
    public Response saveCategory(CategoryDto categoryDto);
    public Response updateCategory(Long id, CategoryDto categoryDto);
    public Response getCategoryById(Long id);
    public Response getAllCategories();
    public Response deleteCategory(Long id);
}
