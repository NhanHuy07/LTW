package com.example.demo.service.impl;

import com.example.demo.constants.ErrorMessage;
import com.example.demo.constants.SuccessMessage;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.EditUserRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getAuthenticatedUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userPrincipal.getUsername());
    }

    @Override
    @Transactional
    public MessageResponse register(UserRequest userRequest) {
        if(userRequest.getPassword() == null || !userRequest.getPassword().equals(userRequest.getPassword2())){
            return new MessageResponse("passwordError", ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return new  MessageResponse("emailError", ErrorMessage.EMAIL_IN_USE);
        }
        User user = modelMapper.map(userRequest, User.class);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new MessageResponse("alert-success", SuccessMessage.REGISTRATION_SUCCESS);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public MessageResponse editUserInfo(EditUserRequest request) {
        User user = getAuthenticatedUser();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        return new MessageResponse("alert-success", SuccessMessage.USER_UPDATED);
    }

    @Override
    @Transactional
    public MessageResponse changePassword(ChangePasswordRequest request) {
        if (request.getPassword() != null && !request.getPassword().equals(request.getPassword2())) {
            return new MessageResponse("passwordError", ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        User user = getAuthenticatedUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return new MessageResponse("alert-success", SuccessMessage.PASSWORD_CHANGED);
    }
}
