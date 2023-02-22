package com.stickerdon.library.service;

import com.stickerdon.library.dto.CategoryDto;
import com.stickerdon.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category>findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enabledById(Long id);
    List<Category>findAllByActivated();

    //Customer
    List<CategoryDto> getCategoryAndProduct();
}
