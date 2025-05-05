package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum Role implements GrantedAuthority {

    USER, ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
