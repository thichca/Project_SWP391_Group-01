package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.UserServiceIpml;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final UserServiceIpml userServiceIpml;

    public ControllerAdvice(UserServiceIpml userServiceIpml) {
        this.userServiceIpml = userServiceIpml;
    }

    @ModelAttribute("user")
    public User getCurrentUser(Principal principal) {
        return (principal != null) ? userServiceIpml.findByUsername(principal.getName()) : null;
    }
}
