package com.example.simplerest.repository.todo;

import com.example.simplerest.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}