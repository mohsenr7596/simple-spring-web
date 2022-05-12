package com.example.simplerest.domain.post;

import com.example.simplerest.domain.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Table
public class Comment {

    @Id
    private Long id;

    @ManyToOne
    private Post post;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String body;
}
