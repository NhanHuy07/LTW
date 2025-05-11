package com.example.demo.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    List<Appointment> findByUser(User user);
    Appointment save(Appointment appointment);
    void deleteById(Long id);
} 