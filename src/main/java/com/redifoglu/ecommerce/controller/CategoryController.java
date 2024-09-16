package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category save(@RequestBody Category category){
        return categoryService.save(category);
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable int id){
        return categoryService.findCategoryById(id);
    }
}
