package com.tavisca.workshops.todoapp.services;

import com.tavisca.workshops.todoapp.model.Todo;
import com.tavisca.workshops.todoapp.repository.TodoDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoDao todoDao;

    public JSONObject getTodos() {
        List<Todo> todos = todoDao.selectAllTodos();
        return new JSONObject().put("todos", todos).put("status", "OK");
    }

    public JSONObject getTodoById(int todoId) {
        Todo todo = todoDao.selectTodoById(todoId);
        String status = (todo != null) ? "OK" : "400";
        return new JSONObject().put("todo", todo).put("status", status);
    }

    public JSONObject addTodo(String json) {
        String todoName = new JSONObject(json).getString("todoname");
        boolean isQuerySuccess = todoDao.insertTodo(todoName);
        String status = (isQuerySuccess) ? "OK" : "500";
        return new JSONObject().put("status", status);
    }

    public JSONObject updateTodo(int todoId, String json) {
        String todoName = new JSONObject(json).getString("todoname");
        boolean isQuerySuccess = todoDao.updateTodoById(todoId, todoName);
        String status = (isQuerySuccess) ? "OK" : "400";
        return new JSONObject().put("status", status);
    }

    public JSONObject deleteTodo(int todoId) {
        boolean isQuerySuccess = todoDao.deleteTodoById(todoId);
        String status = (isQuerySuccess) ? "OK" : "400";
        return new JSONObject().put("status", status);
    }
}