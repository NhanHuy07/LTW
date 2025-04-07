package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "cars")
@Setter
public class Car {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "car_name", nullable = false)
    private String carName;

    @Column(name = "model")
    private String model;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "power")
    private Long power;

    @Column(name = "number_of_seat")
    private String numberOfSeat;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "price")
    private Long price;

}
