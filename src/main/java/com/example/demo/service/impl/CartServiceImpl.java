package com.example.demo.service.impl;

import com.example.demo.entity.Accessory;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    public CartItem addToCart(User user, Accessory accessory, int quantity) {
        Cart cart = getOrCreateCart(user);
        
        // Kiểm tra xem phụ tùng đã có trong giỏ hàng chưa
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getAccessory().getId().equals(accessory.getId()))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // Nếu đã có, cập nhật số lượng
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            entityManager.flush();
            return item;
        } else {
            // Nếu chưa có, tạo mới
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setAccessory(accessory);
            newItem.setQuantity(quantity);
            newItem.setPrice(accessory.getPrice().multiply(BigDecimal.valueOf(quantity)));
            
            cart.addItem(newItem);
            cartRepository.save(cart);
            return newItem;
        }
    }

    @Override
    @Transactional
    public CartItem updateCartItem(Long cartItemId, int quantity) {
        Cart cart = cartRepository.findAll().stream()
                .filter(c -> c.getItems().stream().anyMatch(item -> item.getId().equals(cartItemId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));
        
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));
        
        if (quantity <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(quantity);
            item.setPrice(item.getAccessory().getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        
        cartRepository.save(cart);
        return item;
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId) {
        Cart cart = cartRepository.findAll().stream()
                .filter(c -> c.getItems().stream().anyMatch(item -> item.getId().equals(cartItemId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));
        
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));
        
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }

    @Override
    public BigDecimal getCartTotal(Cart cart) {
        return cart.getItems().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 