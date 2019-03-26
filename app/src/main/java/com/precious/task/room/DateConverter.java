package com.precious.task.room;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;


public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timeStamp){

        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){

        return date == null ? null : date.getTime();
    }
}
