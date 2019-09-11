package com.tavisca.workshops.todoapp;

import com.tavisca.workshops.todoapp.logs.TodoLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoappApplication {
    private static final Logger logger = LoggerFactory.getLogger(TodoLogger.class);

    public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
        logger.info("Sprint Boot Application Started");
    }
}