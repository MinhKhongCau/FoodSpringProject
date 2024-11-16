package com.myproject.SpringStarter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.SpringStarter.Model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    
}
