package org.example.nckh.controller;


import org.example.nckh.model.User;
import org.example.nckh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            User registeredUser = userService.registerUser(user);
            userService.sendVerificationEmail(registeredUser);
            model.addAttribute("message", "Đăng ký thành công! Vui lòng kiểm tra email để xác nhận tài khoản.");
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // Hiển thị trang xác thực
    @GetMapping("/verify")
    public String showVerificationForm() {
        return "verify";
    }

    // Xử lý xác thực
    @PostMapping("/verify")
    public String verifyUser(@RequestParam("code") String code, Model model) {
        boolean verified = userService.verifyUser(code);
        if (verified) {
            model.addAttribute("message", "Tài khoản đã được xác thực thành công!");
        } else {
            model.addAttribute("message", "Mã xác nhận không hợp lệ.");
        }
        return "verify";
    }

    // Trang chủ sau khi đăng nhập thành công
    @GetMapping("/")
    public String home() {
        return "home";
    }
}