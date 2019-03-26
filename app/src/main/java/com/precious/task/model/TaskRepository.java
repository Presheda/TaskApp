package com.precious.task.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.precious.task.room.TaskDao;
import com.precious.task.room.TaskDb;

import java.util.List;

public class TaskRepository {
   private LiveData<List<Task>> loadAllTask;
   private LiveData<Task> loadTaskByid;



    Application application;
    TaskDb taskDb;
    TaskDao taskDao;


    public TaskRepository(Application application) {
        this.application = application;
        taskDb = TaskDb.getInstance(application.getApplicationContext());
        taskDao = taskDb.getTaskDao();
    }

    public LiveData<List<Task>> getLoadAllTask() {
        loadAllTask = taskDao.loadAllTask();

        return loadAllTask;
    }

    public LiveData<Task> getLoadTaskByid(int id){
        loadTaskByid = taskDao.loadTaskById(id);
        return loadTaskByid;
    }

    public void deleteTask(Task task){

        taskDao.deleteTask(task);
    }

    public void insertTask(Task task){
        taskDao.insertTask(task);
    }

    public void updateTask(Task task){
        taskDao.updateTask(task);
    }

}
