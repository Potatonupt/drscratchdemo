package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserRepository;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // 从会话中获取用户名
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("loggedInUsername", username);
        }
        return "index";
    }


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

    @PostMapping("/login")
    public RedirectView loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            userService.loginUser(username, password);
            // 如果登录成功，将用户名保存到会话
            session.setAttribute("username", username);
            return new RedirectView("/", true);
        } catch (Exception e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            return new RedirectView("/login", true);
        }
    }


    @GetMapping("/uploadForm")
    public String showAnotherPage(HttpSession session, Model model) {
        int[] resultArray = (int[]) session.getAttribute("resultArray");

        if (resultArray != null) {
            // 将resultArray传递到另一个页面的模型中
            model.addAttribute("resultArray", resultArray);
        }

        return "uploadForm"; // 返回另一个前端页面
    }

    @GetMapping("/user/{username}")
    public String userProfile(@PathVariable String username, Model model) {
        // 根据用户名查询用户的个人信息，然后将信息传递给模板
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "user_profile"; // 渲染用户的个人资料页
    }




    //主页按钮跳转
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

