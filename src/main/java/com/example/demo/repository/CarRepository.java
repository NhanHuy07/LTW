package com.example.demo.repository;

import com.example.demo.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByIdIn(List<Long> carIds);

    List<Car> findByCarNameContaining(String keyword);

    Page<Car> findAll(Pageable pageable);

//    @Query(
//
//    )
//    Page<Car> getCarByFilterParams(Map<String ,String > params, Pageable pageable);
}
