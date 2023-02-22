package com.stickerdon.library.repository;

import com.stickerdon.library.dto.CategoryDto;
import com.stickerdon.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Transactional
    @Query("SELECT c FROM Category c where c.is_activated = true AND c.is_deleted = false")
    List<Category> findAllByActivated();

    @Query("SELECT new com.stickerdon.library.dto.CategoryDto(c.id, c.name, count(p.category.id)) " +
            "FROM Category c INNER JOIN Product p ON p.category.id = c.id " +
            "WHERE c.is_activated = true AND c.is_deleted = false GROUP BY c.id")
    List<CategoryDto> getCategoryAndProduct();
}
