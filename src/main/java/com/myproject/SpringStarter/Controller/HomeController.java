package com.myproject.SpringStarter.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myproject.SpringStarter.Model.Post;
import com.myproject.SpringStarter.Service.PostService;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;
    
    @GetMapping("/home")
    public String home(Model model) {
        List<Post> posts = postService.getAll();
        model.addAttribute("posts",posts);
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
