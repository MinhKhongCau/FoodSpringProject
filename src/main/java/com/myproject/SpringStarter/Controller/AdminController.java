package com.myproject.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.myproject.SpringStarter.Repository.AuthorityRepository;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {
    @Autowired
    private AuthorityRepository authorityRepository;

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        return "admin";
    }
    
}
