package com.stickerdon.library.service;

import com.stickerdon.library.model.ShoppingCart;

public interface OrderService {
    void saveOrder(ShoppingCart cart);
    void acceptOrder(Long id);
    void cancelOrder(Long id);
}
