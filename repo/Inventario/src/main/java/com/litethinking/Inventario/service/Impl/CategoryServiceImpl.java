package com.litethinking.Inventario.service.Impl;

import com.litethinking.Inventario.dto.CategoryDto;
import com.litethinking.Inventario.model.Category;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.repository.CategoryRepository;
import com.litethinking.Inventario.service.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Saves a new category in the database.
     *
     * @param categoryDto Data Transfer Object containing category information.
     * @return Response indicating the result of the category creation.
     */
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

    /**
     * Updates an existing category identified by its ID.
     *
     * @param id ID of the category to update.
     * @param categoryDto Data Transfer Object containing updated category information.
     * @return Response indicating the result of the update operation.
     */
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

    /**
     * Retrieves a category by its ID.
     *
     * @param id ID of the category to retrieve.
     * @return Response containing the category details or an error message if not found.
     */
    public Response getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(String.valueOf(id));
        if (categoryOptional.isPresent()) {
            return new Response(Collections.singletonList(categoryOptional.get()));
        }
        return new Response(true, "Category not found.");
    }

    /**
     * Retrieves all categories.
     *
     * @return Response containing a list of all categories.
     */
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new Response(categories);
    }

    /**
     * Deletes a category by its ID. This method is marked as @Transactional to ensure that
     * the delete operation is handled within a transaction context.
     *
     * @param id ID of the category to delete.
     * @return Response indicating the result of the delete operation.
     */
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
