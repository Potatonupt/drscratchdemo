package com.example.demo.controller;

import com.example.demo.entity.AnalysisResult;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    private UserService userService;

    @GetMapping("/history")
    public String showHistory(HttpSession session, Model model) {
        // 从 session 中获取当前用户
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            // 获取当前用户的历史分析结果
            List<AnalysisResult> historyResults = userService.getHistoryResults(currentUser);

            // 将结果传递给前端页面
            model.addAttribute("historyResults", historyResults);

            return ""; // 返回展示历史记录的页面
        } else {
            return "redirect:/login"; // 如果用户未登录，重定向到登录页面
        }
    }
}

