package com.myproject.SpringStarter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.SpringStarter.Model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    
}
