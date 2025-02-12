package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Trang login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý đăng ký người dùng
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu không khớp.");
            return "redirect:/register";
        }

        if (userRepository.findByUsername(username) != null) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại.");
            return "redirect:/register";
        }

        if (userRepository.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng.");
            return "redirect:/register";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setRole("ROLE_EMPLOYEE");

        userRepository.save(newUser);
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            return "redirect:/login";
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("success", "Đăng nhập thành công!");
        return "redirect:/admin";
    }
}
