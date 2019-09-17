package com.tavisca.workshops.todoapp;

import com.tavisca.workshops.todoapp.model.Todo;
import com.tavisca.workshops.todoapp.services.TodoService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TodoService todoServiceMock;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getAllTodosTest() throws Exception {
        List<Todo> todos = new ArrayList<Todo>() {{
            add(new Todo(101, "firstTodo"));
            add(new Todo(102, "secondTodo"));
            add(new Todo(103, "thirdTodo"));
        }};
        JSONObject mockJsonResponse = new JSONObject().put("todos", todos).put("status", "OK");
        Mockito.when(todoServiceMock.getTodos()).thenReturn(mockJsonResponse);

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json(mockJsonResponse.toString()));
    }

    @Test
    public void getTodoByIdTest() throws Exception {
        int todoId = 101;
        Todo todo = new Todo(todoId, "firstTodo");
        JSONObject mockJsonResponse = new JSONObject().put("todo", todo).put("status", "OK");
        Mockito.when(todoServiceMock.getTodoById(todoId)).thenReturn(mockJsonResponse);

        mockMvc.perform(get("/todos/" + todoId))
                .andExpect(status().isOk())
                .andExpect(content().json(mockJsonResponse.toString()));
    }

    @Test
    public void addTodoTest() throws Exception {
        JSONObject mockJsonResponse = new JSONObject().put("status", "OK");
        Mockito.when(todoServiceMock.addTodo("{ \"todoname\" : \"addedTodo\" }")).thenReturn(mockJsonResponse);

        String requestBody = "{ \"todoname\" : \"addedTodo\" }";
        mockMvc.perform(post("/todos")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(mockJsonResponse.toString()));
    }

    @Test
    public void updateTodoTest() throws Exception {
        int todoId = 101;
        JSONObject mockJsonResponse = new JSONObject().put("status", "OK");
        Mockito.when(todoServiceMock.updateTodo(todoId, "{ \"todoname\" : \"updatedTodo\" }")).thenReturn(mockJsonResponse);

        String requestBody = "{ \"todoname\" : \"updatedTodo\" }";
        mockMvc.perform(put("/todos/" + todoId)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(mockJsonResponse.toString()));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        int todoId = 101;
        JSONObject mockJsonResponse = new JSONObject().put("status", "OK");
        Mockito.when(todoServiceMock.deleteTodo(todoId)).thenReturn(mockJsonResponse);

        this.mockMvc.perform(delete("/todos/" + todoId))
                .andExpect(status().isOk())
                .andExpect(content().json(mockJsonResponse.toString()));
    }
}