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

//    @GetMapping("/admin/authRoles")
//    public String listUsers(@RequestParam(defaultValue = "0") int page, Model model) {
//        String role = "ROLE_ADMIN";
//        Pageable pageable = PageRequest.of(page, 1, Sort.by("id").ascending());
//        Page<User> usersPage = authRoleService.getUsersByRolePages(role, pageable);
//
//        model.addAttribute("usersPage", usersPage);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", usersPage.getTotalPages());
//        return "authRole";
//    }

    @GetMapping("/admin/authRoles")
    public String listUsers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) String keyword,
                            Model model) {
        String role = "ROLE_ADMIN";
        Pageable pageable = PageRequest.of(page, 1, Sort.by("id").ascending());

        Page<User> usersPage;
        if (keyword != null && !keyword.isEmpty()) {
            usersPage = authRoleService.searchUsersByName(keyword, role, pageable);
        } else {
            usersPage = authRoleService.getUsersByRolePages(role, pageable);
        }

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "authRole";
    }


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