package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(long id) throws NotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new NotFoundException("Category not found with ID: " + id);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(long id, Category updatedCategory) {
        Category category = findCategoryById(id);
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(long id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }
}
