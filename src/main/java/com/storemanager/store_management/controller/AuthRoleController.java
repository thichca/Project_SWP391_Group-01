package com.storemanager.store_management.controller;


import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthRoleController {

    @Autowired
    private AuthRoleService authRoleService;

    @GetMapping("/authRoles")
    public String getUsers( Model model) {
        String role = "ROLE_ADMIN";
        List<User> authRoles;
        if (role != null) {
            authRoles = authRoleService.getUsersByRole(role);
        } else {
            authRoles = authRoleService.getAllUsers();
        }
        model.addAttribute("authRoles", authRoles);
        return "authRole";

    }

    @PostMapping("/updateRole")
    public String updateRole(@RequestParam("id") Long userId,
                             @RequestParam("role") String newRole,
                             RedirectAttributes redirectAttributes) {
        try {
            authRoleService.updateUserRole(userId, newRole);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/authRoles";
    }
}