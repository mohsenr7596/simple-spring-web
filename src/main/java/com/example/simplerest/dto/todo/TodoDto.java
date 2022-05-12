package com.example.simplerest.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto implements Serializable {
    private Long id;
    private String title;
    private boolean completed = false;
    private Long userId;
}
