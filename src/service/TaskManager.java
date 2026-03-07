package service;

import model.Priority;
import model.Status;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    public TaskManager(){
        tasks =new ArrayList<>();
    }
    public void addTask(Task task){
        tasks.add(task);
    }
    public List<Task> getAllTasks(){
        return tasks;
    }
    public void deleteTask(Task task){
        tasks.remove(task);
    }
    public void updateTaskStatus(Task task , Status status){
        task.setStatus(status);
    }
    public void checkOverDue(){
        for (Task task :tasks){
            if (task.getDueDateAndTime().isBefore(LocalDateTime.now())&&task.getStatus() != Status.DONE){
                task.setStatus(Status.OVERDUE);
            }
        }
    }
    public void editTask(Task task , String newTitle , String newDescription , LocalDateTime newDueDateTime , Priority newPriority , Status newStatus){
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setDueDateAndTime(newDueDateTime);
        task.setPriority(newPriority);
        task.setStatus(newStatus);
    }
}
