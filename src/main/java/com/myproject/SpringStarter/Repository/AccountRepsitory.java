package com.myproject.SpringStarter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.SpringStarter.Model.Account;



@Repository
public interface AccountRepsitory extends JpaRepository<Account,Long> {
    Optional<Account> findOneByEmailIgnoreCase(String email);
}
