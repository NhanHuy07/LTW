package com.example.demo.service;

import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.EditUserRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    User getAuthenticatedUser();

    MessageResponse register(UserRequest userRequest);

    List<User> findAll();

    User findByEmail(String email);

    MessageResponse editUserInfo(EditUserRequest request);

    MessageResponse changePassword(ChangePasswordRequest request);
}
