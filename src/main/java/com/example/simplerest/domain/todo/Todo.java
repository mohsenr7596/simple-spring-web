package com.example.simplerest.domain.todo;

import com.example.simplerest.domain.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Table
public class Todo {

    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private boolean completed = false;

    @ManyToOne
    private User user;
}
