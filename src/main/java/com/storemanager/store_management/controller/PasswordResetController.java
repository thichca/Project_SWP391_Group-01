package com.storemanager.store_management.controller;

import com.storemanager.store_management.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    private final PasswordResetService PasswordResetService;

    @Autowired
    public PasswordResetController(PasswordResetService PasswordResetService) {
        this.PasswordResetService = PasswordResetService;
    }

    @GetMapping("/forget-password")
    public String showForgotPasswordForm() {
        return "forgetPassword";
    }




    @PostMapping("/forget-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("message", "Vui lòng nhập email hợp lệ.");
            return "forgetPassword";
        }

        boolean exists = PasswordResetService.checkIdExists(email);

        if (exists) {
            model.addAttribute("email", email);
            return  "resetPassword";
        } else {
            model.addAttribute("message", "Email không tồn tại trong hệ thống.");
            return "forgetPassword";
        }
    }
    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model) {

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("message", "Mật khẩu xác nhận không khớp.");
            model.addAttribute("email", email);
            return "resetPassword";
        }

        boolean isUpdated = PasswordResetService.updatePassword(email, newPassword);

        if (isUpdated) {
            model.addAttribute("message", "Mật khẩu đã được cập nhật thành công.");
            return "login";
        } else {
            model.addAttribute("message", "Có lỗi xảy ra, vui lòng thử lại.");
            model.addAttribute("email", email);
            return "resetPassword";
        }
    }
}