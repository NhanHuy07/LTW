package com.example.demo.repository;

import com.example.demo.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByIdIn(List<Long> carIds);

    List<Car> findByCarNameContaining(String keyword);

    Page<Car> findAll(Pageable pageable);

    List<Car> findByBrandIgnoreCase(String brand);

    @Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findAllBrands();

    @Query("SELECT c FROM Car c WHERE " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:fuel IS NULL OR c.fuel = :fuel) AND " +
            "(:color IS NULL OR c.color = :color) AND " +
            "(:numberOfSeats IS NULL OR c.numberOfSeats = :numberOfSeats)")
    List<Car> findByFilters(
            @Param("brand") String brand,
            @Param("fuel") String fuel,
            @Param("color") String color,
            @Param("numberOfSeats") Long numberOfSeats
    );
}
