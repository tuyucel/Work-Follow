package com.example.turgay.workfollow;

public class TasksAdd {
    private String task, name, time, date;
    public TasksAdd(){
    }
        public TasksAdd(String task, String name, String time, String date){
        this.task = task;
        this.name = name;
        this.time = time;
        this.date = date;

    }

    public String getTask() {
        return task;
    }
    public void setTask
            (String task) {
                this.task = task;
    }

    public String getName() {
        return name;
    }
    public void setName
            (String name) {
                 this.name = name;
    }

    public String getTime() {return time;}
    public void setTime
            (String time) {
                this.time = time;
    }

    public String getDate() {return date;}
    public void setDate
            (String date) {
                 this.date = date;
    }
}