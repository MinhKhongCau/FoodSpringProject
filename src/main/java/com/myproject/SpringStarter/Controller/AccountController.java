package com.myproject.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Service.AccountService;
import com.myproject.SpringStarter.Until.Constants.Roles;

import jakarta.validation.Valid;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "account_view/register";
    }

    @PostMapping("/register")
    public String getRegister(@Valid @ModelAttribute Account account, BindingResult result) {
        if (result.hasErrors()) {
            return "account_view/register";
        }
        System.out.println("***Save account:");
        account.setRole(Roles.USER.getRole());
        accountService.save(account);
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String menu(Model model) {
        return "account_view/login";
    }
    
}
