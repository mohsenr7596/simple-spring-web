package com.example.simplerest.domain.post;


import com.example.simplerest.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table
public class Post {

    @Id
    private Long id;

    @Column
    private String title;

    @Lob
    @Column
    private String body;

    @OneToMany
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    private User user;
}
