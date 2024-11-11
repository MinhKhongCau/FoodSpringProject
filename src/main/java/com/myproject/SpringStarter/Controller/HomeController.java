package com.myproject.SpringStarter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        return "index";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
    @GetMapping("/book")
    public String book(Model model) {
        return "book";
    }
    @GetMapping("/menu")
    public String menu(Model model) {
        return "menu";
    }
}
