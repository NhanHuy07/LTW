package com.example.demo.service.impl;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.User;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    
    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
    
    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    @Override
    public List<Appointment> findByUser(User user) {
        return appointmentRepository.findByUser(user);
    }
    
    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
} 