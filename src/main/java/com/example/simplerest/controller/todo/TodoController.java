package com.example.simplerest.controller.todo;

import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.dto.todo.TodoDto;
import com.example.simplerest.service.todo.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoDto> readAll() {
        return todoService.readAll(null).getContent();
    }

    @GetMapping("/todos/{id}")
    public List<TodoDto> readByUserIdAndCompleted(@RequestParam("userId") long userId, @RequestParam("completed") boolean completed) {
        return todoService.readByUserIdAndCompleted(userId, completed);
    }
}
