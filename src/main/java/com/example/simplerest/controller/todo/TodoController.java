package com.example.simplerest.controller.todo;

import com.example.simplerest.aop.LoggingAspect;
import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.dto.todo.TodoDto;
import com.example.simplerest.service.todo.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoDto> readAll() {
        LOGGER.info("calling read all.");
        return todoService.readAll(null).getContent();
    }

    @GetMapping("/todos/{id}")
    public List<TodoDto> readByUserIdAndCompleted(@RequestParam("userId") long userId, @RequestParam("completed") boolean completed) {
        LOGGER.info("calling find todo with user id.");
        return todoService.readByUserIdAndCompleted(userId, completed);
    }
}
