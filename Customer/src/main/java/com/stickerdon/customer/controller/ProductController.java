package com.stickerdon.customer.controller;

import com.stickerdon.library.model.Product;
import com.stickerdon.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "shop";
    }
}