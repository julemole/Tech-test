package com.litethinking.Inventario.controller;

import com.litethinking.Inventario.dto.CategoryDto;
import com.litethinking.Inventario.model.Response;
import com.litethinking.Inventario.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService categoryServiceImpl;

    public CategoryController(ICategoryService categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    /**
     * Creates a new category.
     *
     * @param categoryDto Data Transfer Object containing category information.
     * @return Response indicating the result of the category creation.
     */
    @PostMapping("/")
    public Response createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryServiceImpl.saveCategory(categoryDto);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id ID of the category to retrieve.
     * @return Response containing the category details or an error message if not found.
     */
    @GetMapping("/{id}")
    public Response getCategory(@PathVariable Long id) {
        return categoryServiceImpl.getCategoryById(id);
    }

    /**
     * Retrieves all categories.
     *
     * @return Response containing a list of all categories.
     */
    @GetMapping
    public Response getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }

    /**
     * Updates an existing category.
     *
     * @param id ID of the category to update.
     * @param categoryDto Data Transfer Object containing updated category information.
     * @return Response indicating the result of the update operation.
     */
    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return categoryServiceImpl.updateCategory(id, categoryDto);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id ID of the category to delete.
     * @return Response indicating the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable Long id) {
        return categoryServiceImpl.deleteCategory(id);
    }
}
