package com.springBoot.controller;


import com.springBoot.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.springBoot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String userPage(Model model, Authentication authentication) {
        model.addAttribute("user", service.getUserByUsername(authentication.getName()));
        return "userPage";
    }
}
