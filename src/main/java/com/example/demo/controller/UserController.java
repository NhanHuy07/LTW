package com.example.demo.controller;


import com.example.demo.constants.Pages;
import com.example.demo.constants.PathConstants;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.EditUserRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ControllerUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/user")
    public String userProfile(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        
        return "user/profile";
    }

    @GetMapping(PathConstants.REGISTRATION)
    public String registration(Model model) {
        // Thêm user vào Model nếu chưa có
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRequest());
        }
        return "register";
    }

    @PostMapping(PathConstants.REGISTRATION)
    public String register(@Valid UserRequest user,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if(controllerUtils.validateInputFields(bindingResult,model,"user", user)){
            return "/register";
        }
        MessageResponse messageResponse = userService.register(user);
        if(controllerUtils.validateInputField(model, messageResponse, "user", user)){
            return "register";
        }
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/login", messageResponse);
    }

    @GetMapping("/user/account")
    public String userAccount(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return Pages.USER_ACCOUNT;
    }

    @GetMapping("/user/info")
    public String userInfo(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("isEditMode", false); // Không phải edit mode
        return Pages.USER_INFO;
    }

    @GetMapping("/user/info/edit")
    public String editUserInfo(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("isEditMode", true); // Đang ở edit mode
        return Pages.USER_INFO_EDIT; // Trả về cùng template user-info
    }

    @PostMapping("/user/info/edit")
    public String editUserInfo(@Valid EditUserRequest request, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "user", request)) {
            return Pages.USER_INFO_EDIT;
        }
        MessageResponse messageResponse = userService.editUserInfo(request);
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/user/info", messageResponse);
    }

    @PostMapping("/user/change/password")
    public String changePassword(@Valid ChangePasswordRequest request, BindingResult bindingResult, Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "request", request)) {
            return Pages.USER_PASSWORD_RESET;
        }
        MessageResponse messageResponse = userService.changePassword(request);
        if (controllerUtils.validateInputField(model, messageResponse, "request", request)) {
            return Pages.USER_PASSWORD_RESET;
        }
        return controllerUtils.setAlertMessage(model, Pages.USER_PASSWORD_RESET, messageResponse);
    }

    @GetMapping("user/reset")
    public String passwordReset() {
        return Pages.USER_PASSWORD_RESET;
    }
}
