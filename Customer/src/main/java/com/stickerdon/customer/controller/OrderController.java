package com.stickerdon.customer.controller;

import com.stickerdon.library.model.Customer;
import com.stickerdon.library.model.Order;
import com.stickerdon.library.model.ShoppingCart;
import com.stickerdon.library.service.CustomerService;
import com.stickerdon.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    private CustomerService customerService;
    private OrderService orderService;

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if (customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()) {

            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information after checkout!");
            return "account";
        } else {
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getShoppingCart();
            model.addAttribute("cart", cart);
        }
        return "checkout";
    }


    @GetMapping("/order")
    public String order(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders", orderList);

        return "order";
    }

    @GetMapping("/save-order")
    public String saveOrder(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart cart = customer.getShoppingCart();
        orderService.saveOrder(cart);
        return "redirect:/order";
    }
}
