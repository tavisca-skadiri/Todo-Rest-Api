package com.tavisca.workshops.todoapp;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoappApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
        LoggerFactory.getLogger(TodoappApplication.class).info("Sprint Boot Application Started");
    }
}