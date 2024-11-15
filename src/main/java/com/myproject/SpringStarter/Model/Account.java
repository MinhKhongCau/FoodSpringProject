package com.myproject.SpringStarter.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    
    private String email;
    private String password;
    private String firstname;

    // @OneToMany(mappedBy = "account")
    // private List<Post> posts;

    public Account(String email, String password, String firstname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
    }

    
}
