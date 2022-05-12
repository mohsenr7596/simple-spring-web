package com.example.simplerest.domain.user;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
public class User {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String email;
}
