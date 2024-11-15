package com.myproject.SpringStarter.SeedData;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Model.Post;
import com.myproject.SpringStarter.Service.AccountService;
import com.myproject.SpringStarter.Service.PostService;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    /**
     * Auto add 2 records when application started
    */    
    @Override
    public void run(String... args) throws Exception {
        List<Post> listPost = postService.getAll();
        if (listPost.size() == 0) {
            System.out.println("***Initialized data in this Application");
            Account account = new Account("account01@studyeasy.org","password","user01");
            accountService.save(account);
            account = new Account("account02@studyeasy.org","password","user02");
            accountService.save(account);

            Post post = new Post("Post 01","Post 01 Body ................");
            postService.save(post);
            post =  new Post("Post 02","Post 02 Body ................");
            postService.save(post);
        }
    }
    
}
