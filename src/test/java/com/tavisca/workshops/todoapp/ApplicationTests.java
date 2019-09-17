package com.tavisca.workshops.todoapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodoappApplication.class)
public class ApplicationTests {

    @Test
    public void test() {
        TodoappApplication.main(new String[]{});
    }
}
