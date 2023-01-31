package com.stickerdon.library.repository;

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
}
