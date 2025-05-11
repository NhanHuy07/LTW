package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;
    
    @Column(name = "accessory_name", nullable = false)
    private String accessoryName;
    
    @Column(name = "accessory_category")
    private String accessoryCategory;
    
    @Column(name = "accessory_image")
    private String accessoryImage;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // Convenience method to calculate subtotal
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (price != null && quantity != null) {
            subtotal = price.multiply(new BigDecimal(quantity));
        }
    }
} 