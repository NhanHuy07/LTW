package com.example.demo.service;

import com.example.demo.entity.Car;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Car getCarById(Long carId);

    Optional<Car> findById(Long id);

    List<Car> getPopularCars();

    List<Car> searchCar(String keyword);

    Page<Car> getAll(Integer pageNumber);

    Page<Car> searchCar(String keyword, Integer pageNumber);

    List<Car> findAll();

    Page<Car> findByBrand(String brand, Integer pageNumber);

    List<String> getAllBrands();

    Page<Car> findByFilters(String brand, String fuel, String color, Long numberOfSeats, Integer pageNumber);
}
