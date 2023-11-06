package com.example.demo.controller;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {

    /*
     * http://localhost:9012/012-springboot-thymeleaf/springboot/thymeleaf/index
     * */
    @RequestMapping(value = "")
    public String index(HttpServletRequest request, Model model) {

        model.addAttribute("data", "恭喜您，SpringBoot集成Thymeleaf成功");

        return "index";
    }
}
