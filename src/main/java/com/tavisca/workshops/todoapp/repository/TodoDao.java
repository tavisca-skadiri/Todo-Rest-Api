package com.tavisca.workshops.todoapp.repository;

import com.tavisca.workshops.todoapp.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TodoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Todo> selectAllTodos() {
        String sql = "SELECT * FROM TODOS";
        List<Todo> todos = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            Todo todo = new Todo(
                    (int) row.get("id"),
                    (String) row.get("todoname")
            );
            todos.add(todo);
        }
        return todos;
    }

    public Todo selectTodoById(int todoId) {
        try {
            String sql = "SELECT * FROM TODOS WHERE ID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{todoId}, (row, rowNum) ->
                    new Todo(
                            row.getInt("id"),
                            row.getString("todoname")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean insertTodo(String todoName) {
        try {
            String SQL = "INSERT INTO TODOS(todoname) VALUES (\"" + todoName + "\")";
            return jdbcTemplate.update(SQL) != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean updateTodoById(int todoId, String todoName) {
        try {
            String sql = "UPDATE TODOS SET todoname = ? WHERE ID = ?";
            return jdbcTemplate.update(sql, todoName, todoId) != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean deleteTodoById(int todoId) {
        try {
            String sql = "DELETE FROM TODOS WHERE ID = ?";
            return jdbcTemplate.update(sql, todoId) != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}