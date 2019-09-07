package com.tavisca.workshops.todoapp.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {
    private List<String> todos = new ArrayList<>();

    @GetMapping(path = "/todos")
    public ResponseEntity<?> getTodos() {
        return sendResponse("All todos retrieved");
    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") int todoid) {
        if (todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        JSONObject jsonResponse = new JSONObject()
                .put("todoname", todos.get(todoid))
                .put("status", "Todo " + (todoid + 1) + " retrieved")
                .put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<?> addTodo(@RequestBody String json) {
        JSONObject obj = new JSONObject(json);
        String todoname = obj.getString("todoname");
        todos.add(todoname);
        return sendResponse("Todo " + todoname + " Added");
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") int todoid, @RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        if (todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        String todoname = jsonObject.getString("todoname");
        todos.set(todoid, todoname);
        return sendResponse("Todo-" + (todoid + 1) + " Updated");
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoid) {
        if (todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        todos.remove(todoid);
        return sendResponse("Todo-" + (todoid + 1) + " Deleted");
    }

    private ResponseEntity<?> sendResponse(String status) {
        JSONObject jsonResponse = new JSONObject()
                .put("todos", todos)
                .put("status", status)
                .put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }
}