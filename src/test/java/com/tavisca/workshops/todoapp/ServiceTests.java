package com.tavisca.workshops.todoapp;

import com.tavisca.workshops.todoapp.model.Todo;
import com.tavisca.workshops.todoapp.repository.TodoDao;
import com.tavisca.workshops.todoapp.services.TodoService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTests {
    @Autowired
    private TodoService todoService;

    @MockBean
    private TodoDao todoDao;

    @Test
    public void testSelectAllTodos() {
        List<Todo> todos = new ArrayList<Todo>() {{
            add(new Todo(101, "firstTodo"));
            add(new Todo(102, "secondTodo"));
            add(new Todo(103, "thirdTodo"));
        }};
        JSONObject mockJsonResponse = new JSONObject().put("todos", todos).put("status", "OK");
        Mockito.when(todoDao.selectAllTodos()).thenReturn(todos);

        JSONObject response = todoService.getTodos();

        Assert.assertEquals(mockJsonResponse.toString(), response.toString());
        Mockito.verify(todoDao, Mockito.times(1)).selectAllTodos();
    }

    @Test
    public void testGetTodoById() {
        int todoId = 101;
        Todo todo = new Todo(todoId, "firstTodo");
        JSONObject mockJsonResponse = new JSONObject().put("todo", todo).put("status", "OK");
        Mockito.when(todoDao.selectTodoById(todoId)).thenReturn(todo);

        JSONObject response = todoService.getTodoById(todoId);

        Assert.assertEquals(mockJsonResponse.toString(), response.toString());
        Mockito.verify(todoDao, Mockito.times(1)).selectTodoById(todoId);
    }

    @Test
    public void testInsertTodo() {
        String todoName = "insertedTodo";
        JSONObject mockJsonResponse = new JSONObject().put("status", "OK");
        Mockito.when(todoDao.insertTodo(todoName)).thenReturn(true);

        String requestBody = "{ \"todoname\" : \"insertedTodo\" }";
        JSONObject response = todoService.addTodo(requestBody);

        Assert.assertEquals(mockJsonResponse.toString(), response.toString());
        Mockito.verify(todoDao, Mockito.times(1)).insertTodo(todoName);
    }

    @Test
    public void testUpdateTodoById() {
        int todoId = 101;
        String todoName = "updatedTodo";
        JSONObject mockJsonResponse = new JSONObject().put("status", "OK");
        Mockito.when(todoDao.updateTodoById(todoId, todoName)).thenReturn(true);

        String requestBody = "{ \"todoname\" : \"updatedTodo\" }";
        JSONObject response = todoService.updateTodo(todoId, requestBody);

        Assert.assertEquals(mockJsonResponse.toString(), response.toString());
        Mockito.verify(todoDao, Mockito.times(1)).updateTodoById(todoId, todoName);
    }

    @Test
    public void testDeleteTodoById() {
        int todoId = 101;
        JSONObject mockJsonResponse = new JSONObject().put("status", "OK");
        Mockito.when(todoDao.deleteTodoById(todoId)).thenReturn(true);

        JSONObject response = todoService.deleteTodo(todoId);

        Assert.assertEquals(mockJsonResponse.toString(), response.toString());
        Mockito.verify(todoDao, Mockito.times(1)).deleteTodoById(todoId);
    }
}
