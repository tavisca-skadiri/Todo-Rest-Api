package com.tavisca.workshops.todoapp.services;

import com.tavisca.workshops.todoapp.controllers.TodoController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private List<String> todos = new ArrayList<>();

    public ResponseEntity<?> getTodos() {
        return sendResponse("All Todos retrieved");
    }

    public ResponseEntity<?> getTodoById(int todoid) {
        if (todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        JSONObject jsonResponse = new JSONObject()
                .put("todoname", todos.get(todoid))
                .put("status", "Todo " + (todoid + 1) + " retrieved")
                .put("timestamp", Instant.now().toString());
        logger.info("Todo " + (todoid + 1) + " retrieved");
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }

    public ResponseEntity<?> addTodo(String json) {
        JSONObject obj = new JSONObject(json);
        String todoname = obj.getString("todoname");
        todos.add(todoname);
        return sendResponse("Todo " + todoname + " Added");
    }

    public ResponseEntity<?> updateTodo(int todoid, String json) {
        JSONObject jsonObject = new JSONObject(json);
        if (todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        String todoname = jsonObject.getString("todoname");
        todos.set(todoid, todoname);
        return sendResponse("Todo-" + (todoid + 1) + " Updated");
    }

    public ResponseEntity<?> deleteTodo(int todoid) {
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
        logger.info(status);
        return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
    }
}