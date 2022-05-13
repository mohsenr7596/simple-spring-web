package com.example.simplerest.repository.todo;

import com.example.simplerest.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserIdAndCompleted(long id, boolean completed);

}