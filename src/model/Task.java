package model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private LocalDateTime dueDateAndTime;
    private Priority priority;
    private Status status;

    public Task(String title, String description, LocalDateTime dueDateAndTime,  Priority priority , Status status) {
        this.title = title;
        this.status = status;
        this.dueDateAndTime = dueDateAndTime;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDateAndTime() {
        return dueDateAndTime;
    }

    public void setDueDateAndTime(LocalDateTime dueDateAndTime) {
        this.dueDateAndTime = dueDateAndTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title + " | " + priority + " | " + status;
    }
}
