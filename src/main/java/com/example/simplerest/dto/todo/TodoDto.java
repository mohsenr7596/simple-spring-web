package com.example.simplerest.dto.todo;

import com.example.simplerest.domain.todo.Todo;
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

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.isCompleted();
        this.userId = todo.getUser().getId();
    }
}
