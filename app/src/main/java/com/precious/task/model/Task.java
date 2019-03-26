package com.precious.task.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String taskTitle;
    private Date dateLastUpdated;
    private int taskPriority;


    @Ignore
    public Task(String taskTitle, Date dateLastUpdated, int taskPriority) {
        this.taskTitle = taskTitle;
        this.dateLastUpdated = dateLastUpdated;
        this.taskPriority = taskPriority;
    }


    public Task(int id, String taskTitle, Date dateLastUpdated, int taskPriority) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.dateLastUpdated = dateLastUpdated;
        this.taskPriority = taskPriority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }
}
