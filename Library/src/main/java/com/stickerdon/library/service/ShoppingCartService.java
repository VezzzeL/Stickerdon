package com.stickerdon.library.service;

import com.stickerdon.library.model.Customer;
import com.stickerdon.library.model.Product;
import com.stickerdon.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);
    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);
    ShoppingCart deleteItemFromCart(Product product, Customer customer);
}
