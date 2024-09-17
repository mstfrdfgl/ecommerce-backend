package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.CategoryDTO;
import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.mapper.CategoryMapper;
import com.redifoglu.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) {
        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(CategoryMapper.entityToDto(category), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryDTO = CategoryMapper.entityToDto(category);
            categoryDTOS.add(categoryDTO);
        }
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
//        categoryService.deleteCategory(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
