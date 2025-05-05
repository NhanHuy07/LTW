package com.example.demo.controller;


import com.example.demo.constants.PathConstants;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.service.UserService;
import com.example.demo.utils.ControllerUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ControllerUtils controllerUtils;

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
}
