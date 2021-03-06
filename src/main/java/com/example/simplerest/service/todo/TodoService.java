package com.example.simplerest.service.todo;

import com.example.simplerest.domain.todo.Todo;
import com.example.simplerest.dto.todo.TodoDto;
import com.example.simplerest.repository.todo.TodoRepository;
import com.example.simplerest.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;


    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public long create(TodoDto dto) {
        var todo = new Todo();
        fillEntity(todo, dto);
        todoRepository.save(todo);
        return todo.getId();
    }

    public Page<TodoDto> readAll(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return todoRepository.findAll(pageable).map(TodoDto::new);
    }


    public TodoDto readById(long id) {
        return todoRepository.findById(id).map(TodoDto::new).orElse(null);
    }

    public void deleteById(long id) {
        todoRepository.deleteById(id);
    }

    public boolean updateById(long id, TodoDto dto) {
        return todoRepository.findById(id).map(e -> {
            fillEntity(e, dto);
            return true;
        }).orElse(false);
    }

    public void fillEntity(Todo todo, TodoDto dto) {
        todo.setCompleted(dto.isCompleted());
        todo.setTitle(dto.getTitle());
        todo.setUser(userRepository.findById(dto.getId()).orElse(null));
    }

    public List<TodoDto> readByUserIdAndCompleted(long id, boolean completed) {
        return todoRepository.findByUserIdAndCompleted(id, completed).stream().map(TodoDto::new).collect(Collectors.toList());
    }
}
