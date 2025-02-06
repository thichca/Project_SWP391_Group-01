package com.storemanager.store_management.controller;

import com.storemanager.store_management.entity.User;
import com.storemanager.store_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Trang login
    @GetMapping("/login")
    public String login() {
        return "login";  // Chỉ tới trang login.html
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Trả về trang đăng ký với đối tượng user
    }

    // Xử lý đăng ký người dùng
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirm_password,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        // Kiểm tra mật khẩu có khớp không
        if (!password.equals(confirm_password)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu không khớp.");
            return "redirect:/register";
        }

        // Kiểm tra username và email đã tồn tại chưa
        if (userRepository.findByUsername(username) != null) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại.");
            return "redirect:/register";
        }

        if (userRepository.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng.");
            return "redirect:/register";
        }

        // Tạo mới người dùng và lưu vào cơ sở dữ liệu
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(new BCryptPasswordEncoder().encode(password));  // Mã hóa mật khẩu
        newUser.setEmail(email);
        newUser.setRole("employee");

        userRepository.save(newUser);
        return "redirect:/login";  // Chuyển hướng về trang login sau khi đăng ký thành công
    }

    //Xử lý đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        return "redirect:/admin"; // Chuyển hướng đến trang admin sau khi đăng nhập thành công
    }

}
