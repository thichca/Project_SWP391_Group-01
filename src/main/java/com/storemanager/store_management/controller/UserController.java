package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.UserServiceIpml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {

    @Autowired
    private UserServiceIpml userServiceIpml;

    @GetMapping("/profile")
    public String getUserProfile(Model model, HttpExchange.Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userServiceIpml.findByUsername(username);
            model.addAttribute("user", user);
        }
        return "profile";
    }
}
