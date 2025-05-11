package com.example.demo.service;

import com.example.demo.entity.Accessory;

import java.util.List;
import java.util.Optional;

public interface AccessoryService {
    List<Accessory> findAll();
    Optional<Accessory> findById(Long id);
    List<Accessory> findByCategory(String category);
    Accessory save(Accessory accessory);
    void deleteById(Long id);
} 