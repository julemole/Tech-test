package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CategoryDto;
import com.litethinking.Inventario.model.Category;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CategoryRepository;
import com.litethinking.Inventario.service.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Response saveCategory(CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDto.getName());
        if (existingCategory.isPresent()) {
            return new Response(true, "Error: Category with the provided name already exists.");
        }

        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new Response("Category successfully created.");
    }

    public Response updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(id));
        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            existingCategory.setName(categoryDto.getName());
            categoryRepository.save(existingCategory);
            return new Response("Category successfully updated.");
        }
        return new Response(true, "Category not found.");
    }

    public Response getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(id));
        if (categoryOptional.isPresent()) {
            return new Response(Collections.singletonList(categoryOptional.get()));
        }
        return new Response(true, "Category not found.");
    }

    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new Response(categories);
    }

    @Transactional
    public Response deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(id));
        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
            return new Response("Category successfully deleted.");
        }
        return new Response(true, "Category not found.");
    }
}
