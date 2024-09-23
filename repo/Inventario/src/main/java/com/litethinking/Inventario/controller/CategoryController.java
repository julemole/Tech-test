package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CategoryDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryServiceImpl;

    @PostMapping("/")
    public Response createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryServiceImpl.saveCategory(categoryDto);
    }

    @GetMapping("/{id}")
    public Response getCategory(@PathVariable Long id) {
        return categoryServiceImpl.getCategoryById(id);
    }

    @GetMapping
    public Response getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }

    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return categoryServiceImpl.updateCategory(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable Long id) {
        return categoryServiceImpl.deleteCategory(id);
    }
}
