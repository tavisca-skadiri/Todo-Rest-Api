package com.tavisca.workshops.todoapp;

import com.tavisca.workshops.todoapp.services.TodoService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getAllTodosTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{
            add("firsttodo");
        }};
        JSONObject mockJsonResponse = new JSONObject()
                .put("todos", mockTodoList)
                .put("status", "All todos retrieved")
                .put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);

        given(todoService.getTodos()).willReturn(responseEntity);
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }

    @Test
    public void getTodoByIdTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{
            add("first");
            add("second");
            add("third");
        }};
        int todoid = 1;
        JSONObject mockJsonResponse = new JSONObject()
                .put("todoname", mockTodoList.get(todoid))
                .put("status", "Todo " + (todoid + 1) + " retrieved")
                .put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);

        given(todoService.getTodoById(todoid)).willReturn(responseEntity);
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }

    @Test
    public void addTodoTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{
            add("addedTodo");
        }};
        JSONObject mockJsonResponse = new JSONObject()
                .put("todos", mockTodoList)
                .put("status", "All todos retrieved")
                .put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);

        given(todoService.addTodo("{ \"todoname\" : \"addedTodo\" }")).willReturn(responseEntity);
        String requestBody = "{ \"todoname\" : \"addedTodo\" }";
        mockMvc.perform(post("/todos")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }

    @Test
    public void updateTodoTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{
            add("updatedTodo");
        }};
        JSONObject mockJsonResponse = new JSONObject()
                .put("todos", mockTodoList)
                .put("status", "All todos retrieved")
                .put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);

        given(todoService.updateTodo(0, "{ \"todoname\" : \"updatedTodo\" }")).willReturn(responseEntity);
        String requestBody = "{ \"todoname\" : \"updatedTodo\" }";
        mockMvc.perform(put("/todos/0")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{
            add("first");
            add("second");
            add("third");
        }};
        int todoid = 1;
        JSONObject jsonObject = new JSONObject()
                .put("todoname", mockTodoList.get(todoid))
                .put("status", "Todo " + (todoid + 1) + " has been deleted")
                .put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

        given(todoService.deleteTodo(todoid)).willReturn(responseEntity);
        this.mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }
}