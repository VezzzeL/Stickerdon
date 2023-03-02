package com.stickerdon.library.service.impl;

import com.stickerdon.library.model.*;
import com.stickerdon.library.repository.CartItemRepository;
import com.stickerdon.library.repository.OrderDetailRepository;
import com.stickerdon.library.repository.OrderRepository;
import com.stickerdon.library.repository.ShoppingCartRepository;
import com.stickerdon.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    CartItemRepository cartItemRepository;
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public OrderServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository,
                            CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void saveOrder(ShoppingCart cart) {
        Order order = new Order();
        Customer customer = cart.getCustomer();
        order.setOrderStatus("Pending");
        order.setOrderDate(new Date());
        order.setCustomer(customer);
        order.setTotalPrice(cart.getTotalPrices());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItem()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getProduct().getCostPrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartItemRepository.delete(cartItem);
        }
        order.setOrderDetailList(orderDetailList);
        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        shoppingCartRepository.save(cart);
        orderRepository.save(order);

    }

    @Override
    public void acceptOrder(Long id) {
        Order order = orderRepository.getReferenceById(id);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING");
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
