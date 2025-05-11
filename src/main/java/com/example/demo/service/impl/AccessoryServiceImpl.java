package com.example.demo.service.impl;

import com.example.demo.entity.Accessory;
import com.example.demo.repository.AccessoryRepository;
import com.example.demo.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;

    @Override
    public List<Accessory> findAll() {
        return accessoryRepository.findAll();
    }

    @Override
    public Optional<Accessory> findById(Long id) {
        return accessoryRepository.findById(id);
    }

    @Override
    public List<Accessory> findByCategory(String category) {
        return accessoryRepository.findByCategory(category);
    }

    @Override
    public Accessory save(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    @Override
    public void deleteById(Long id) {
        accessoryRepository.deleteById(id);
    }
} 