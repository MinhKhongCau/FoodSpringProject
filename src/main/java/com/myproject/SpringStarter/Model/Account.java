package com.myproject.SpringStarter.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    
    @Column(unique = true)
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String role;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "account_authority",
        joinColumns = {
            @JoinColumn(name="account-id",referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="authority_id",referencedColumnName = "id")
        }
    )
    private Set<Authority> authorities = new HashSet<>();

    public Account(String email, String password, String firstname, String lastname, String role) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }
    
}
