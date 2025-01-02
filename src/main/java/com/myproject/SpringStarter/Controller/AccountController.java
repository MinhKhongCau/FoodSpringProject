package com.myproject.SpringStarter.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Service.AccountService;
import com.myproject.SpringStarter.Service.EmailService;
import com.myproject.SpringStarter.Until.AppUtils;
import com.myproject.SpringStarter.Until.RandomString;
import com.myproject.SpringStarter.Until.Constants.Roles;
import com.myproject.SpringStarter.Until.Email.EmailData;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class AccountController {
    @Value("${spring.mvc.static-path-pattern}")
    private String uploadDir;

    @Value("${password.token.reset.timeout.minute}")
    private int tokenTimeOut;

    @Value("${site.domain}")
    private String siteDomain;

    @Autowired 
    private EmailService emailService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "account_view/register";
    }

    @PostMapping("/register")
    public String getRegister(@Valid @ModelAttribute Account account, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "account_view/register";
        }
        System.out.println("***Save account:");
        Optional<Account> accouOptional = accountService.getByEmail(account.getEmail());
        if (accouOptional.isPresent()) {
            attributes.addFlashAttribute("error", "This email was register");
            return "redirect:/register";
        }
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
            System.out.println("*** Load account is: "+account.getFirstname());
            
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
    RedirectAttributes attributes,Principal principal) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "No file upload");
            return "redirect:/profile";
        } else {
            try {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                System.out.println("*** File uploads: "+filename);
                int length = 10;
                String randomString = RandomString.getRandomString(length);
                String finalFileString = randomString + filename;
                String fileLocation = AppUtils.getUploadPath(finalFileString);

                Path path = Paths.get(fileLocation);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                attributes.addFlashAttribute("message","You successfully upload");
                System.out.println("*** Path was prepare: "+ fileLocation);

                String email = "email";
                if (principal != null) {
                    email = principal.getName();
                }
                Optional<Account> optionalAccount = accountService.getByEmail(email);
                
                if (optionalAccount.isPresent()) {
                    Account uploadAccount = optionalAccount.get();
                    Account account_by_id = accountService.getById(uploadAccount.getId()).get();
                    String relativePath = uploadDir.replace("**", "uploads/"+finalFileString);
                    System.out.println("*** Relative path prepare: "+relativePath);
                    account_by_id.setPhoto(relativePath);
                    accountService.save(account_by_id);
                }
                return "redirect:/profile";

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return "redirect:/profile";
    }
    
    @GetMapping("/forgot-password")
    public String fogotPassword(Model model) {
        return "account_view/forgot_password";
    }

    @PostMapping("/forgot-password")
    public String resetPasswordHandler(Model model, @RequestParam String email, RedirectAttributes attributes) {
        //TODO: process POST request
        Optional<Account> accountOptional = accountService.getByEmail(email);
        if (accountOptional.isPresent()) {
            String resetToken = UUID.randomUUID().toString();
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(tokenTimeOut);
            Account account = accountService.getById(accountOptional.get().getId()).get();
            account.setResetPasswordToken(resetToken);
            account.setResetPasswordExpiry(expiry);
            accountService.save(account);

            // Add email service send message
            String resetPasswordMessage = "This is the reset password link: "+ siteDomain +"change-password?token="+resetToken;
            EmailData emailData = new EmailData(email, resetPasswordMessage, "Reset password notification by QungMinh Application");
            if (emailService.sendSimpleEmail(emailData) == false) {
                attributes.addFlashAttribute("error", "Fail sending email, contact admin");
                return "redirect:/forgot-password";
            }

            attributes.addFlashAttribute("message", "Email was send");
            return "redirect:/forgot-password";
        } else {
            attributes.addFlashAttribute("error", "Email not available");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/change-password")
    public String change_password(Model model, @RequestParam("token") String token,
    RedirectAttributes attributes) {
        Optional<Account> accountOptional = accountService.getByToken(token);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(accountOptional.get().getResetPasswordExpiry())) {
                attributes.addFlashAttribute("error","Token was expired");
                return "redirect:/forgot-password";
            }
            model.addAttribute("account",account);
            return "account_view/change_password";
        }

        attributes.addFlashAttribute("error","Invalid token");
        return "redirect:/forgot-password";
    }
    
    @PostMapping("/change-password")
    public String postMethodName(@ModelAttribute("account") Account account, RedirectAttributes attributes) {
        Optional<Account> accountOptional = accountService.getById(account.getId());

        if (accountOptional.isPresent()) {
            System.out.println(account.getId() + " " + account.getPassword() + " " + account.getEmail());
            Account reAccount = accountOptional.get();
            reAccount.setPassword(account.getPassword());
            reAccount.setResetPasswordExpiry(null);
            reAccount.setResetPasswordToken("");
            accountService.save(reAccount);

            attributes.addFlashAttribute("message","Password updated !!!");
            return "redirect:/login";
        }

        attributes.addFlashAttribute("error","Change password unsuccessfull ");
        return "redirect:/login";
    }
    
    
}
