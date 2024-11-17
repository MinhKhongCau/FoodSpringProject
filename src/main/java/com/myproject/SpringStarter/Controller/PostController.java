package com.myproject.SpringStarter.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.myproject.SpringStarter.Model.Post;
import com.myproject.SpringStarter.Service.PostService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PostController {
    
    @Autowired
    private PostService postService;

    @GetMapping("/post/{id}")
    public String getMethodName(
        @PathVariable Long id,
        Model model) {
        
            Optional<Post> optionalPost = postService.getById(id);
            
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                model.addAttribute("post", post);
                return "post_view/post";
            } else {
                return "404";
            }
    }
     
}
