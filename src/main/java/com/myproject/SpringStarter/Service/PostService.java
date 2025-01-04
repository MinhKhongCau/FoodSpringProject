package com.myproject.SpringStarter.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.myproject.SpringStarter.Model.Post;
import com.myproject.SpringStarter.Repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Page<Post> getByPage(int offset, int pageSize, String field) {
        return postRepository.findAll(PageRequest.of(offset, pageSize).withSort(Direction.ASC,field));
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void save(Post post) {
        if (post.getId() == null) {
            post.setCreateAt(LocalDateTime.now());
            System.out.println("***Post data was saved");
        }
        postRepository.save(post);
    }
}
