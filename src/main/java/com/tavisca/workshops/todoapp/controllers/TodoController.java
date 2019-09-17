package com.tavisca.workshops.todoapp.controllers;

import com.tavisca.workshops.todoapp.services.TodoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping(path = "/todos")
    public ResponseEntity<String> get() {
        JSONObject jsonResponse = todoService.getTodos();
        return sendResponse(jsonResponse);
    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int todoId) {
        JSONObject jsonResponse = todoService.getTodoById(todoId);
        return sendResponse(jsonResponse);
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<String> add(@RequestBody String jsonRequest) {
        JSONObject jsonResponse = todoService.addTodo(jsonRequest);
        return sendResponse(jsonResponse);
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int todoId, @RequestBody String jsonRequest) {
        JSONObject jsonResponse = todoService.updateTodo(todoId, jsonRequest);
        return sendResponse(jsonResponse);
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoId) {
        JSONObject jsonResponse = todoService.deleteTodo(todoId);
        return sendResponse(jsonResponse);
    }

    private ResponseEntity<String> sendResponse(JSONObject jsonResponse) {
        HttpStatus httpStatus = jsonResponse.get("status").equals("OK") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(jsonResponse.toString(), httpStatus);
    }
}