package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.service.UserServiceIpml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileController {

    @Autowired
    private UserServiceIpml userServiceIpml;

    // Hiển thị trang cập nhật thông tin
    @GetMapping("/profile")
    public String showUpdateForm(Model model) {
        User user = userServiceIpml.getCurrentUser(); // Lấy người dùng hiện tại
        model.addAttribute("user", user);
        return "profile";
    }

    // Xử lý cập nhật thông tin người dùng
    @PostMapping("/update")
    public String updateProfile(@RequestParam("avatar") MultipartFile avatar,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {

        User currentUser = userServiceIpml.getCurrentUser(); // Lấy người dùng hiện tại

        // Cập nhật mật khẩu nếu người dùng nhập mật khẩu mới
        if (newPassword != null && !newPassword.isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(newPassword);
                currentUser.setPassword(encodedPassword); // Lưu mật khẩu mới
            } else {
                model.addAttribute("error", "Mật khẩu không khớp.");
                return "profile";
            }
        }

        // Cập nhật avatar nếu người dùng tải lên avatar mới
        if (!avatar.isEmpty()) {
            try {
                String avatarUrl = userServiceIpml.saveAvatar(avatar); // Lưu avatar và trả về URL
                currentUser.setAvatar(avatarUrl);
            } catch (Exception e) {
                model.addAttribute("error", "Lỗi khi tải lên avatar.");
                return "profile";
            }
        }

        // Lưu thông tin người dùng cập nhật
        userServiceIpml.updateUser(currentUser);

        model.addAttribute("user", currentUser);
        return "redirect:/profile"; // Chuyển hướng đến trang hồ sơ sau khi cập nhật
    }
}
