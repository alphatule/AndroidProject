package com.example.projectofinalm8;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public LiveData<Task> getTaskById(int id) {
        return repository.getTaskById(id);
    }

    public void update(Task task) {
        System.out.println("TaskViewModel update " + task);
        repository.update(task);
    }

    public void insert(Task task) {
        repository.insert(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
}