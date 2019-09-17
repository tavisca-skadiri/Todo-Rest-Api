package com.tavisca.workshops.todoapp.model;

public class Todo {
    private int todoid;
    private String todoname;

    public Todo(int todoid, String todoname) {
        this.todoid = todoid;
        this.todoname = todoname;
    }

    @Override
    public String toString() {
        return "{" +
                "todoid=" + todoid +
                ", todoname='" + todoname + '\'' +
                '}';
    }

    public int getTodoid() {
        return todoid;
    }

    public String getTodoname() {
        return todoname;
    }

}
