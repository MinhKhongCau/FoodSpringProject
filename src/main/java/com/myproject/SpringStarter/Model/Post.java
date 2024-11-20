package com.myproject.SpringStarter.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id",nullable = false)
    private Account account;
    
    public Post(String title, String body,Account account) {
        this.title = title;
        this.body = body;
        this.account = account;
    }
}
