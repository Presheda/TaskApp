package com.precious.task.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {



    LiveData<List<Task>> allTask;
    LiveData<Task> taskById;
    private final TaskRepository taskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }


    public LiveData<List<Task>> getAllTask() {

        allTask = taskRepository.getLoadAllTask();

        return allTask;
    }

    public LiveData<Task> getTaskById(int id) {

        taskById = taskRepository.getLoadTaskByid(id);

        return taskById;
    }

    public void deleteTask(Task task){

        taskRepository.deleteTask(task);
    }

    public void insertTask(Task task){
        taskRepository.insertTask(task);
    }

    public void updateTask(Task task){
        taskRepository.updateTask(task);
    }
}
