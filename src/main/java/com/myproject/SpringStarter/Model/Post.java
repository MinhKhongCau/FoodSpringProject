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
    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }
    @ManyToOne
    @JoinColumn(name = "account",referencedColumnName = "id",nullable = true)
    private Account account;

}
