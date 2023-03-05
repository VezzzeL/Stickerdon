package com.stickerdon.admin.controller;

import com.stickerdon.library.model.Order;
import com.stickerdon.library.repository.OrderRepository;
import com.stickerdon.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    private OrderService orderService;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public String orders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orders", orders);
        return "/orders";
    }

    @GetMapping("/orders-done")
    public String ordersDone(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orders", orders);
        return "/orders-done";
    }

    @GetMapping("/cancel-order/{id}")
    public String cancelOrder(@PathVariable("id") Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @PostMapping("/accept-order/{id}")
    public String acceptOrder(@PathVariable("id") Long id){
        Order order = orderRepository.getReferenceById(id);
        order.setOrderStatus("Order accepted");
        orderRepository.save(order);
        return "redirect:/orders";
    }
    @PostMapping("/complete-order/{id}")
    public String completeOrder(@PathVariable("id") Long id){
        Order order = orderRepository.getReferenceById(id);
        order.setOrderStatus("COMPLETED");
        orderRepository.save(order);
        return "redirect:/orders";
    }
}
