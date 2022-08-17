package com.example.koshar_3375mt;

public class Task {

    String TaskName;
    Integer TaskStaredCount;

    public Task(String taskName, Integer taskStaredCount) {
        TaskName = taskName;
        TaskStaredCount = taskStaredCount;
    }

    public Integer getTaskStaredCount() {
        return TaskStaredCount;
    }

    public void setTaskStaredCount(Integer taskStaredCount) {
        TaskStaredCount = taskStaredCount;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

}
