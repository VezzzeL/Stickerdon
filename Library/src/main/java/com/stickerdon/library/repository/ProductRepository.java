package com.stickerdon.library.repository;

import com.stickerdon.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Admin

    @Query("SELECT p FROM Product p")
    Page<Product> pageProduct(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.description LIKE %?1% OR p.name LIKE %?1%")
    Page<Product> searchProducts(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.description LIKE %?1% OR p.name LIKE %?1%")
    List<Product> searchProductsList(String keyword);

    //Customer

    @Query("SELECT p FROM Product p WHERE p.is_activated = true AND p.is_deleted = false")
    List<Product> getAllProducts();

    @Query(value = "SELECT * FROM products p WHERE p.is_deleted = false AND p.is_activated = true " +
            "ORDER BY rand() asc limit 4 ", nativeQuery = true)
    List<Product> listViewProducts();

    @Query(value = "SELECT * FROM products p INNER JOIN categories c on c.category_id = p.category_id " +
            "WHERE p.category_id = ?1", nativeQuery = true)
    List<Product> getRelatedProducts(Long categoryId);

    @Query(value = "SELECT p FROM Product p INNER JOIN Category c on c.id = p.category.id WHERE c.id = ?1 " +
            "AND p.is_deleted = false AND p.is_activated = true")
    List<Product> getProductsInCategory(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.is_activated = true AND p.is_deleted = false " +
            "ORDER BY p.costPrice desc")
    List<Product> filterHighPrice();

    @Query("SELECT p FROM Product p WHERE p.is_activated = true AND p.is_deleted = false ORDER BY p.costPrice ")
    List<Product> filterLowPrice();
}
