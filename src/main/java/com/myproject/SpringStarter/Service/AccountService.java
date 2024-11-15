package com.myproject.SpringStarter.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Repository.AccountRepsitory;

@Service
public class AccountService {
    @Autowired
    private AccountRepsitory accountRepsitory;

    public Optional<Account> getById(Long id) {
        return accountRepsitory.findById(id);
    }

    public List<Account> getAll() {
        return accountRepsitory.findAll();
    }

    public Account save(Account account) {
        return accountRepsitory.save(account);
    }

    public void delete(Account account) {
        accountRepsitory.delete(account);
    }

    public void getByEmail(String email) {
        
    }

}
