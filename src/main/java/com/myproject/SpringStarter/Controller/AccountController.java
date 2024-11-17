package com.myproject.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Service.AccountService;
import com.myproject.SpringStarter.Until.Constants.Roles;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public String getRegister(@ModelAttribute Account account) {
        System.out.println("***Save account:");
        account.setRole(Roles.USER.getRole());
        accountService.save(account);
        return "redirect:/home";
    }
    
}
