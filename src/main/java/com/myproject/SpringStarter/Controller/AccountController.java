package com.myproject.SpringStarter.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Service.AccountService;
import com.myproject.SpringStarter.Until.Constants.Roles;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AccountController {
    @Value("spring.mvc.static-path-pattern")
    private String uploadDir;

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

    /**
     * This is controller to get and edit profile
     * @author: Quang Minh
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, Principal principal) {
        String auth = "email";
        if (principal != null) {
            auth = principal.getName();
        }
        Optional<Account> accountOptional = accountService.getByEmail(auth);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            System.out.println("***"+account.getFirstname());
            
            model.addAttribute("account", account);
            return "account_view/profile";
        }
        return "/404";
    }

    @PostMapping("/profile")
    public String profileHandler(@Valid @ModelAttribute Account account, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "account_view/profile";
        }
        String email = "email";
        if (principal != null) {
            email = principal.getName();
        }
        if (account.getEmail().compareToIgnoreCase(email) < 0) {
            return "redirect:/?error";
        }
        Optional<Account> optionalAccount = accountService.getById(account.getId());
        if (optionalAccount.isPresent()) {
            Account editAccount = optionalAccount.get();
            editAccount.setEmail(account.getEmail());
            editAccount.setPassword(account.getPassword());
            editAccount.setFirstname(account.getFirstname());
            editAccount.setLastname(account.getLastname());
            editAccount.setAge(account.getAge());
            editAccount.setBirthDate(account.getBirthDate());
            editAccount.setGender(account.getGender());
            accountService.save(editAccount);
        }
        System.out.println("***Update account:");
        accountService.save(account);
        return "redirect:/login";
    }
    
    @PostMapping("/profile/photo")
    public String loadPhotoHandle(@RequestParam("file") MultipartFile file,
    @RequestAttribute RedirectAttributes attributes,Principal principal) {
        if (file.isEmpty()) {
            attributes.addAttribute("error", "No file upload");
            return "redirect:/profile";
        } else {
            // String filename = StringUtils.cleanPath(file.getOriginalFilename());

            
            
        }
        
        return null;
    }
    
}
