package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.User;

public interface UserService {
    User getAuthenticatedUser();

    MessageResponse register(UserRequest userRequest);

}
