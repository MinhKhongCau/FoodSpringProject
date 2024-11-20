package com.myproject.SpringStarter.Config;

import java.util.ArrayList;
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
        // List<Post> listPost = postService.getAll();
        
        // System.out.println("***Initialized data in this Application");

        // for (Privillage pri: Privillage.values()) {
        //     Authority authority = new Authority();
        //     authority.setId(pri.getId());
        //     authority.setName(pri.getName());

        //     authorityService.save(authority);
        // }

        // Account account1 = new Account("account01@studyeasy.org","password","user01","lname01",Roles.USER.getRole());
        // Account account2 = new Account("account02@studyeasy.org","password","user02","lname02",Roles.USER.getRole());
        // Account account3 = new Account("admin01@studyeasy.org","admin","admin01","lname01",Roles.ADMIN.getRole());
        // Account account4 = new Account("admin02@studyeasy.org","admin","admin02","lname01",Roles.EDITOR.getRole());

        
        // Set<Authority> authorities = new HashSet<>();
        // authorityService.findById(Privillage.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
        // authorityService.findById(Privillage.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        
        // account3.setAuthorities(authorities);
        // account4.setAuthorities(authorities);
        
        // accountService.save(account1);
        // accountService.save(account2);
        // accountService.save(account3);
        // accountService.save(account4);
        
        // if (listPost.size() == 0) {    
        //     Post post1 = new Post("Post 01","Post 01 Body ................",account1);
        //     postService.save(post1);
        //     Post post2 =  new Post("Post 02","Post 02 Body ................",account1);
        //     postService.save(post2);
        //     Post post3 = new Post("Post 03","Post 03 Body ................",account3);
        //     postService.save(post3);
        //     Post post4 =  new Post("Post 04","Post 04 Body ................",account3);
        //     postService.save(post4);
        // }
    }
    
}
