package com.stickerdon.library.service.impl;

import com.stickerdon.library.dto.CategoryDto;
import com.stickerdon.library.model.Category;
import com.stickerdon.library.repository.CategoryRepository;
import com.stickerdon.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        try{
            Category categorySave = new Category(category.getName());
            return categoryRepository.save(categorySave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = categoryRepository.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.set_deleted(true);
        category.set_activated(false);
        categoryRepository.save(category);
    }

    @Override
    public void enabledById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.set_activated(true);
        category.set_deleted(false);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return categoryRepository.findAllByActivated();
    }


    //Customer
    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }
}
