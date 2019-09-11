package com.tavisca.workshops.todoapp.controllers;

import com.tavisca.workshops.todoapp.logs.TodoLogger;
import com.tavisca.workshops.todoapp.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoLogger.class);

    @Autowired
    TodoService todoService;

    @GetMapping(path = "/todos")
    public ResponseEntity<?> get() {
        logger.info("GET Request - All Todos");
        return todoService.getTodos();
    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int todoid) {
        logger.info("GET Request - Get Todo No-" + (todoid+1));
        return todoService.getTodoById(todoid);
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<?> add(@RequestBody String json) {
        logger.info("POST Request - Add new Todo");
        logger.info("Request Body - " + json);
        return todoService.addTodo(json);
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int todoid, @RequestBody String json) {
        logger.info("PUT Request - Update Todo No-" + (todoid+1));
        logger.info("Request Body - " + json);
        return todoService.updateTodo(todoid, json);
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoid) {
        logger.info("DELETE Request - Delete Todo No-" + (todoid+1));
        return todoService.deleteTodo(todoid);
    }
}