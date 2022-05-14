package com.example.simplerest.repository.todo;

import com.example.simplerest.domain.todo.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    // JUnit test for saveTodo
    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveTodoTest() {

        Todo todo = new Todo();

        todo.setCompleted(true);
        todo.setTitle("TODO 1 Test");

        todoRepository.save(todo);

        Assertions.assertThat(todo.getId()).isPositive();
    }

    @Test
    @Order(2)
    public void getTodoTest() {

        Todo todo = todoRepository.findById(1L).get();

        Assertions.assertThat(todo.getId()).isEqualTo(1L);

    }

    @Test
    @Order(3)
    public void getListOfTodosTest() {

        List<Todo> todos = todoRepository.findAll();

        Assertions.assertThat(todos.size()).isPositive();

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTodoTest() {

        Todo todo = todoRepository.findById(1L).get();

        todo.setTitle("new title");

        Todo todoUpdated = todoRepository.save(todo);

        Assertions.assertThat(todoUpdated.getTitle()).isEqualTo("new title");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteTodoTest() {

        Todo todo = todoRepository.findById(1L).get();

        todoRepository.delete(todo);

        //todoRepository.deleteById(1L);

        Todo todo1 = null;

        Optional<Todo> optionalTodo = todoRepository.findById(1L);

        if (optionalTodo.isPresent()) {
            todo1 = optionalTodo.get();
        }

        Assertions.assertThat(todo1).isNull();
    }
}