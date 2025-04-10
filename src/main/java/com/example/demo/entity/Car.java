package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@Table(name = "cars")
@Setter
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type; // old, new

    @Column(name = "car_name")
    private String carName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "engine_size")
    private Long engineSize;

    @Column(name = "power")
    private String power;

    @Column(name = "gearbox")
    private String gearbox;

    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Column(name = "doors")
    private String doors;

    @Column(name = "color")
    private String color;

    @Column(name = "filename")
    private String fileName;

    @ElementCollection
    @CollectionTable(
            name = "car_descriptions",
            joinColumns = @JoinColumn(name = "car_id")
    )
    @Column(name = "description")
    private List<String> description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private CarOwner owner;
}
