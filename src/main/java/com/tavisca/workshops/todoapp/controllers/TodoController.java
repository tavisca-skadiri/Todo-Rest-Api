package com.tavisca.workshops.todoapp.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TodoController {
    private List<String> todos = new ArrayList<>();

    @GetMapping(path = "/todos")
    public ResponseEntity<?> getTodos(){
        return sendResponse("All todos retrieved");
    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") int todoid){
        Map<String, String> responseMap = new HashMap<>();
        if(todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        responseMap.put("todoname", todos.get(todoid));
        responseMap.put("status", "Todo "+(todoid+1)+" retrieved");
        responseMap.put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<?> addTodo(@RequestBody String json) {
        JSONObject obj = new JSONObject(json);
        String todoname = obj.getString("todoname");
        todos.add(todoname);
        return sendResponse("Todo-"+todoname+" Added");
    }

    @PatchMapping(path = "/todos/{id}")
    public ResponseEntity<?> updatetodo(@PathVariable("id") int todoid,@RequestBody String json){
        JSONObject jsonObject = new JSONObject(json);
        if(todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        String todoname = jsonObject.getString("todoname");
        todos.set(todoid,todoname);
        return sendResponse("Todo-"+(todoid+1)+" Updated");
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoid){
        if(todoid >= todos.size())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        todos.remove(todoid);
        return sendResponse("Todo-"+(todoid+1)+" Deleted");
    }

    private ResponseEntity<?> sendResponse(String status) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("todos", todos.toString());
        responseMap.put("status", status);
        responseMap.put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}