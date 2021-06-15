package com.girish.todoister.util;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static Date fromTimeStamp(Long value){
        return value==null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date==null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority==null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priorityName){
        return priorityName==null ? null : Priority.valueOf(priorityName);
    }

}
