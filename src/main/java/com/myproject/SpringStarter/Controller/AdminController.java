package com.myproject.SpringStarter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        return "admin";
    }
    
}
