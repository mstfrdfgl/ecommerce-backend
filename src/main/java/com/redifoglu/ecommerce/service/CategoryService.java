package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(long id){
        Optional<Category> category =categoryRepository.findById(id);
        if(category.isPresent()){
        return category.get();
        }
        throw new RuntimeException();
    }

    @Transactional
    public Category save(Category category){
        return categoryRepository.save(category);
    }
}
