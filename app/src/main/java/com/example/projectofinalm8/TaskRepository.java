package com.example.projectofinalm8;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getTasks(); // Obtenemos todas las tareas
    }

    public void insert(Task task) {
        // Operación asincrónica para insertar la tarea
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    // Clase interna para hacer la inserción en segundo plano (async)
    private static class InsertTaskAsyncTask extends android.os.AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insertTask(tasks[0]);
            return null;
        }
    }
}