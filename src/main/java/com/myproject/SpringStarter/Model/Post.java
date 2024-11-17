package com.myproject.SpringStarter.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "account",referencedColumnName = "id",nullable = false)
    private Account account;
    
    public Post(String title, String body,Account account) {
        this.title = title;
        this.body = body;
        this.account = account;
    }
}
