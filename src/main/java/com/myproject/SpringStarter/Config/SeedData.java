package com.myproject.SpringStarter.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Model.Authority;
import com.myproject.SpringStarter.Model.Post;
import com.myproject.SpringStarter.Service.AccountService;
import com.myproject.SpringStarter.Service.AuthorityService;
import com.myproject.SpringStarter.Service.PostService;
import com.myproject.SpringStarter.Until.Constants.Privillage;
import com.myproject.SpringStarter.Until.Constants.Roles;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * Auto add 2 records when application started
    */    
    @Override
    public void run(String... args) throws Exception {
        List<Post> listPost = postService.getAll();
        if (listPost.size() == 0) {
            System.out.println("***Initialized data in this Application");

            for (Privillage pri: Privillage.values()) {
                Authority authority = new Authority();
                authority.setId(pri.getId());
                authority.setName(pri.getName());

                authorityService.save(authority);
            }

            Account account1 = new Account("account01@studyeasy.org","password","user01","lname01",Roles.USER.getRole());
            Account account2 = new Account("account02@studyeasy.org","password","user02","lname02",Roles.USER.getRole());
            Account account3 = new Account("admin01@studyeasy.org","admin","admin01","lname01",Roles.EDITOR.getRole());
            Account account4 = new Account("admin02@studyeasy.org","admin","admin02","lname01",Roles.EDITOR.getRole());
            
            Post post = new Post("Post 01","Post 01 Body ................");
            postService.save(post);
            post =  new Post("Post 02","Post 02 Body ................");
            
            Set<Authority> authorities = new HashSet<>();
            authorityService.findById(Privillage.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
            authorityService.findById(Privillage.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
            
            account3.setAuthorities(authorities);
            account4.setAuthorities(authorities);
            
            accountService.save(account1);
            accountService.save(account2);
            accountService.save(account3);
            accountService.save(account4);
            postService.save(post);
        }
    }
    
}
