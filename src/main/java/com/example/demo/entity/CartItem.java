package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "accessory_id", nullable = false)
    private Accessory accessory;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(nullable = false)
    private BigDecimal price;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        if (accessory != null) {
            this.price = accessory.getPrice().multiply(BigDecimal.valueOf(quantity));
        }
    }
} 