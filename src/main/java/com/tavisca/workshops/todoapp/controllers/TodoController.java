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
    private List<String> todoList = new ArrayList<>();

    @GetMapping(path = "/todoList")
    public ResponseEntity<?> getTodoList() {
        return sendResponse("All todoList retrieved");
    }

    @GetMapping(path = "/todoList/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") int todoId) {
        if (todoId >= todoList.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        JSONObject jsonResponse = new JSONObject()
                .put("todoName", todoList.get(todoId))
                .put("status", "Todo " + (todoId + 1) + " retrieved")
                .put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/todoList")
    public ResponseEntity<?> addTodo(@RequestBody String json) {
        JSONObject jsonRequest = new JSONObject(json);
        String todoName = jsonRequest.getString("todoname");
        todoList.add(todoName);
        return sendResponse("Todo " + todoName + " Added");
    }

    @PutMapping(path = "/todoList/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") int todoId, @RequestBody String json) {
        if (todoId >= todoList.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        JSONObject jsonObject = new JSONObject(json);
        String todoName = jsonObject.getString("todoname");
        todoList.set(todoId, todoName);
        return sendResponse("Todo-" + (todoId + 1) + " Updated");
    }

    @DeleteMapping(path = "/todoList/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoId) {
        if (todoId >= todoList.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        todoList.remove(todoId);
        return sendResponse("Todo-" + (todoId + 1) + " Deleted");
    }

    private ResponseEntity<?> sendResponse(String status) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("todoList", todoList);
        jsonResponse.put("status", status);
        jsonResponse.put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }
}