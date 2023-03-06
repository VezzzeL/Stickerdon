package com.stickerdon.customer.controller;

import com.stickerdon.library.dto.ProductDto;
import com.stickerdon.library.model.Category;
import com.stickerdon.library.model.Customer;
import com.stickerdon.library.model.ShoppingCart;
import com.stickerdon.library.service.CategoryService;
import com.stickerdon.library.service.CustomerService;
import com.stickerdon.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private ProductService productService;
    private CategoryService categoryService;
    private CustomerService customerService;

    @Autowired
    public HomeController(ProductService productService, CategoryService categoryService, CustomerService customerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.customerService = customerService;
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getShoppingCart();
            session.setAttribute("totalItems", cart.getTotalItems());
        } else {
            session.removeAttribute("username");
        }
        return "home";
    }

    @GetMapping("/home")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }
}

