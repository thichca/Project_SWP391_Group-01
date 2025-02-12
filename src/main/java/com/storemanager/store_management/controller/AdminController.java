package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.UserServiceIpml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserServiceIpml userServiceIpml;

    @GetMapping("/admin")
    public String adminPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userServiceIpml.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "admin";
    }


}
