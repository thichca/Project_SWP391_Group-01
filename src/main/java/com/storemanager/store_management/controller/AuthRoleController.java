package com.storemanager.store_management.controller;


import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //    @GetMapping("/authRoles")
//    public String getUsers( Model model) {
//        String role = "ROLE_ADMIN";
//        List<User> authRoles;
//        if (role != null) {
//            authRoles = authRoleService.getUsersByRole(role);
//        } else {
//            authRoles = authRoleService.getAllUsers();
//        }
//        model.addAttribute("authRoles", authRoles);
//        return "authRole";
//
//    }
    @GetMapping("/admin/authRoles")
    public String listUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        String role = "ROLE_ADMIN";
        Pageable pageable = PageRequest.of(page, 2, Sort.by("id").ascending());  // Mỗi trang sẽ hiển thị 10 người dùng
        Page<User> usersPage = authRoleService.getUsersByRolePages(role, pageable);

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "authRole";
    }
//
//    @GetMapping("/authRoles")
//    public String listUsers(@RequestParam(defaultValue = "0") int page, Model model) {
//        String role = "ROLE_ADMIN";
//        List<User> authRoles;
//        if (role != null) {
//            authRoles = authRoleService.getUsersByRole(role);
//        } else {
//            authRoles = authRoleService.getAllUsers();
//        }
//        model.addAttribute("authRoles", authRoles);
//        return "authRole";
//        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());  // Mỗi trang sẽ hiển thị 10 người dùng
//        Page<User> usersPage = authRoleService.getUsersByRolePages(role, pageable);
//
//        model.addAttribute("usersPage", usersPage);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", usersPage.getTotalPages());
//        return "authRole";
//    }

    @PostMapping("/admin/updateRole")
    public String updateRole(@RequestParam("id") Long userId,
                             @RequestParam("role") String newRole,
                             RedirectAttributes redirectAttributes) {
        try {
            authRoleService.updateUserRole(userId, newRole);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/authRoles";
    }
}