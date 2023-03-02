package com.stickerdon.library.service.impl;

import com.stickerdon.library.model.CartItem;
import com.stickerdon.library.model.Order;
import com.stickerdon.library.model.OrderDetail;
import com.stickerdon.library.model.ShoppingCart;
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
        order.setOrderStatus("Pending");
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
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
}
