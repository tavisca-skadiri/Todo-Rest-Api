package com.tavisca.workshops.todoapp.controllers;

import com.tavisca.workshops.todoapp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {
    @Autowired
    TodoService todoService;
    @GetMapping(path = "/todos")
    public ResponseEntity<?> get() {
        return todoService.getTodos();
    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int todoid) {
        return todoService.getTodoById(todoid);
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<?> add(@RequestBody String json) {
        return todoService.addTodo(json);
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int todoid, @RequestBody String json) {
        return todoService.updateTodo(todoid, json);
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoid) {
        return todoService.deleteTodo(todoid);
    }
}