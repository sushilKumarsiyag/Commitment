package com.aunico.os.commitment;

public class Datareterive {

    private String Task,date,time,priority;

    public Datareterive()
    {

    }

    public Datareterive(String task, String date, String time, String priority) {
        Task = task;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
