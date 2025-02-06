package com.example.projectofinalm8;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private boolean isCompleted;

    public Task(String title) {
        this.title = title;
        this.description = "description";
        this.isCompleted = true;
    }

    // Constructor para actualización (Ignorado por Room)
    @Ignore
    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

//    public Task(String title, String description, boolean isCompleted) {
//        this.title = title;
//        this.description = description;
//        this.isCompleted = isCompleted;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
