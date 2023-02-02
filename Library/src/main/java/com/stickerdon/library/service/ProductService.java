package com.stickerdon.library.service;

import com.stickerdon.library.dto.ProductDto;
import com.stickerdon.library.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    ProductDto getById(Long id);
    Product save(ProductDto productDto, MultipartFile imageProduct);
    Product update(MultipartFile imageProduct, ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
}