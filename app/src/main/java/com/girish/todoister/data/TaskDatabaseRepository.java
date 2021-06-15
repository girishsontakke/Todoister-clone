package com.girish.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.girish.todoister.model.Task;
import com.girish.todoister.util.TaskRoomDatabase;

import java.util.List;

import static com.girish.todoister.util.TaskRoomDatabase.databaseWriter;

public class TaskDatabaseRepository {
    private final TaskRoomDatabase taskRoomDatabase;
    private final LiveData<List<Task>> allTasks;
    private final TaskDao taskDao;
    public TaskDatabaseRepository(Application application){
        taskRoomDatabase = TaskRoomDatabase.getInstance(application);
        taskDao = taskRoomDatabase.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public void insert(Task task){
        databaseWriter.execute(()->{
            taskDao.insertTask(task);
        });
    }

    public void update(Task task){
        databaseWriter.execute(()->{
            taskDao.updateTask(task);
        });
    }

    public void delete(Task task){
        databaseWriter.execute(()->{
            taskDao.deleteTask(task);
        });
    }

    public LiveData<Task> get(long id){
        return taskDao.get(id);
    }

}
