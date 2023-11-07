package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            userService.registerUser(username, password);
            model.addAttribute("message", "Registration successful. You can now log in.");
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
        }
        return "register";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
//        try {
//            userService.loginUser(username, password);
//            model.addAttribute("message", "Login successful.");
//        } catch (Exception e) {
//            model.addAttribute("error", "Login failed: " + e.getMessage());
//        }
//        return "login";
//    }

    @PostMapping("/login")
    public RedirectView loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            userService.loginUser(username, password);
            // 如果登录成功，重定向到index页面
            return new RedirectView("/", true);
        } catch (Exception e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            // 如果登录失败，返回到login页面
            return new RedirectView("/login", true);
        }
    }

    @GetMapping("/toLogin")
    public RedirectView toLogin() {
        // 如果需要在此处执行登录逻辑，请在此处添加登录逻辑
        // 重定向到/login页面
        return new RedirectView("/login", false);
    }

    @GetMapping("/toRegister")
    public RedirectView toRegister() {
        // 如果需要在此处执行登录逻辑，请在此处添加登录逻辑
        // 重定向到/login页面
        return new RedirectView("/register", false);
    }
}

