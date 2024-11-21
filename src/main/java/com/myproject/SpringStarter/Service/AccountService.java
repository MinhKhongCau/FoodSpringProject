package com.myproject.SpringStarter.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.SpringStarter.Model.Account;
import com.myproject.SpringStarter.Model.Authority;
import com.myproject.SpringStarter.Repository.AccountRepsitory;

@Service
public class AccountService implements UserDetailsService {
    
    @Autowired
    private AccountRepsitory accountRepsitory;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Account> getById(Long id) {
        return accountRepsitory.findById(id);
    }

    public List<Account> getAll() {
        return accountRepsitory.findAll();
    }

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        System.out.println("***"+account.toString());

        return accountRepsitory.save(account);
    }

    public void delete(Account account) {
        accountRepsitory.delete(account);
    }

    public Optional<Account> getByEmail(String email) {
        return accountRepsitory.findOneByEmailIgnoreCase(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<Account> optionalAccount = accountRepsitory.findOneByEmailIgnoreCase(email);
        if (!optionalAccount.isPresent()) {
            System.out.println("***Load user: "+optionalAccount.get().getEmail());
            throw new UsernameNotFoundException(email);
        }
        
        Account account = optionalAccount.get();
        if (account.getPhoto() == null) {
            account.setPhoto("/images/person_img.png");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole()));

        for (Authority auth: account.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(auth.getName()));
        }

        System.out.println("***Load user: "+account.toString());
        return new User(account.getEmail(),account.getPassword(),grantedAuthorities);
    }

}
