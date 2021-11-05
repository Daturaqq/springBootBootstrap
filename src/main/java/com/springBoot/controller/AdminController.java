package com.springBoot.controller;

import com.springBoot.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.springBoot.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;

    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String mainPage(Model model, Authentication authentication) {
        model.addAttribute("user", service.getUserByUsername(authentication.getName()));
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", service.getRoleList());
        return "adminPage";
    }

    @GetMapping("/{id}")
    public String userPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", service.getUser(id));
        return "userPage";
    }

    @PostMapping("/")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam(value = "roles") String[] roles) {
        service.add(user, roles);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User editUser,
                             @PathVariable("id") Long id,
                             @RequestParam(value = "roles") String[] role,
                             Authentication authentication) {
        long authenticationUserId = service.getUserByUsername(authentication.getName()).getId();
        service.edit(editUser, id, role);
        if (id == authenticationUserId) {
            return "redirect:/login?logout";
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id,
                             Authentication authentication) {
        long authenticationUserId = service.getUserByUsername(authentication.getName()).getId();
        service.delete(id);
        if (id == authenticationUserId) {
            return "redirect:/login?logout";
        }
        return "redirect:/admin";
    }
}
