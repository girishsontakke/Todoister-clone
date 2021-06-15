package com.girish.todoister.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.girish.todoister.data.TaskDao;
import com.girish.todoister.model.Task;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    private static volatile TaskRoomDatabase INSTANCE;
    private final static int NUMBER_OF_THREADS = 4;
    public static ExecutorService databaseWriter =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    private final static Callback taskRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriter.execute(()->{
                        TaskDao taskDao = INSTANCE.taskDao();
                        taskDao.deleteAll();
                    });
                }
            };

    public static TaskRoomDatabase getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class){
                if(INSTANCE==null)
                    INSTANCE = Room.databaseBuilder(application.getApplicationContext(),
                        TaskRoomDatabase.class, "toister_database").build();
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();
}
