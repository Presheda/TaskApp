package com.precious.task.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.precious.task.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TaskDb extends RoomDatabase {


    private static final String DATABASENAME = "task";
    private static final Object LOCK = new Object();
    private static  TaskDb databaseInstance;


    public static TaskDb getInstance(Context context){
        if(databaseInstance == null){
            synchronized (LOCK){
                databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TaskDb.class,
                        DATABASENAME
                        ).build();
            }

        }
        return databaseInstance;
    }



    public abstract TaskDao getTaskDao();
}
