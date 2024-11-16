package com.myproject.SpringStarter.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.SpringStarter.Model.Authority;
import com.myproject.SpringStarter.Repository.AuthorityRepository;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public void save(Authority authority) {
        authorityRepository.save(authority);
    }

    public Optional<Authority> findById(Long id) {
        // TODO Auto-generated method stub
        return authorityRepository.findById(id);
    }
    
}
