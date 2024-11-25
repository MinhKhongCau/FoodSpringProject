package com.myproject.SpringStarter.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true)
    @NotEmpty(message = "Email missing")
    private String email;

    @NotEmpty(message = "Pass missing")
    private String password;

    @NotEmpty(message = "First name missing")
    private String firstname;

    private String lastname;

    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Integer age;

    private String gender;
    
    private String photo;

    private String resetPasswordToken;

    private LocalDateTime resetPasswordExpiry;

    @OneToMany(mappedBy = "account")
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
