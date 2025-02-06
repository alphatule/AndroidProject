package com.example.projectofinalm8;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    LiveData<Task> getTaskById(int taskId);

    @Update
    void update(Task task);
}
