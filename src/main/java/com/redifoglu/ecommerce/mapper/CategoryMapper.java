package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.CategoryDTO;
import com.redifoglu.ecommerce.entity.Category;

import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDTO entityToDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getProducts().stream()
                        .map(ProductMapper::entityToDto)
                        .collect(Collectors.toList())
        );
    }

    public static Category dtoToEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.id());
        category.setName(categoryDTO.name());
        category.setProducts(categoryDTO.products().stream()
                .map(ProductMapper::dtoToEntity)
                .collect(Collectors.toList()));
        return category;
    }
}
