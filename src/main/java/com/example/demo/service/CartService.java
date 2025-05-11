package com.example.demo.service;

import com.example.demo.entity.Accessory;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartService {
    Cart getOrCreateCart(User user);
    CartItem addToCart(User user, Accessory accessory, int quantity);
    CartItem updateCartItem(Long cartItemId, int quantity);
    void removeCartItem(Long cartItemId);
    void clearCart(User user);
    BigDecimal getCartTotal(Cart cart);
} 