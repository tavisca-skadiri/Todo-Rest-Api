package com.tavisca.workshops.todoapp;


import com.tavisca.workshops.todoapp.services.TodoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ServiceTests {
    @Test
    public void testAddTodoById() {
        TodoService todoService = new TodoService();
        ResponseEntity<?> responseEntity = todoService.addTodo("{ \"todoname\" : \"newTodo\" }");
        Assert.assertTrue(responseEntity.getBody().toString().contains("newTodo"));
    }

    @Test
    public void testGetTodos() {
        TodoService todoService = new TodoService();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.getTodos();
        Assert.assertTrue(responseEntity.getBody().toString().contains("firstTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("secondTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("thirdTodo"));
    }

    @Test
    public void testGetTodoById() {
        TodoService todoService = new TodoService();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.getTodoById(1);
        Assert.assertFalse(responseEntity.getBody().toString().contains("firstTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("secondTodo"));
        Assert.assertFalse(responseEntity.getBody().toString().contains("thirdTodo"));
    }

    @Test
    public void testUpdateTodoById() {
        TodoService todoService = new TodoService();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.updateTodo(1, "{ \"todoname\" : \"updatedTodo\" }");
        Assert.assertTrue(responseEntity.getBody().toString().contains("firstTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("updatedTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("thirdTodo"));
    }

    @Test
    public void testDeleteTodoById() {
        TodoService todoService = new TodoService();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.deleteTodo(1);
        Assert.assertTrue(responseEntity.getBody().toString().contains("firstTodo"));
        Assert.assertFalse(responseEntity.getBody().toString().contains("secondTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("thirdTodo"));
    }

    @Test
    public void testGetWhenNoTodoAdded() {
        TodoService todoService = new TodoService();
        ResponseEntity responseEntity = todoService.getTodoById(1);
        Assert.assertEquals(400,responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateWhenNoTodoAdded() {
        TodoService todoService = new TodoService();
        ResponseEntity responseEntity = todoService.updateTodo(1, "{ \"todoname\" : \"updatedTodo\" }");
        Assert.assertEquals(400,responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteWhenNoTodoAdded() {
        TodoService todoService = new TodoService();
        ResponseEntity responseEntity = todoService.deleteTodo(1);
        Assert.assertEquals(400,responseEntity.getStatusCodeValue());
    }
}
