package com.stickerdon.library.service.impl;

import com.stickerdon.library.dto.ProductDto;
import com.stickerdon.library.model.Product;
import com.stickerdon.library.repository.ProductRepository;
import com.stickerdon.library.service.ProductService;
import com.stickerdon.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ImageUpload imageUpload;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ImageUpload imageUpload) {
        this.productRepository = productRepository;
        this.imageUpload = imageUpload;
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(product.getImage());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getReferenceById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActivated(product.is_activated());
        return productDto;
    }

    @Override
    public Product save(ProductDto productDto, MultipartFile imageProduct) {
        try {
            Product product = new Product();
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if (imageUpload.uploadImage(imageProduct)) {
                    System.out.println("uploaded");
                }
                imageUpload.uploadImage(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.getReferenceById(productDto.getId());
            if (imageProduct == null) {
                product.setImage(productDto.getImage());
            } else {
                if (imageUpload.checkExisted(imageProduct) == false) {
                    imageUpload.uploadImage(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalePrice());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }
}
