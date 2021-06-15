package com.girish.todoister.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.girish.todoister.data.TaskDatabaseRepository;
import com.girish.todoister.model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static TaskDatabaseRepository taskDatabaseRepository;
    public static LiveData<List<Task>> allTasks;
    public TaskViewModel(Application application) {
        super(application);
        taskDatabaseRepository = new  TaskDatabaseRepository(application);
        allTasks = taskDatabaseRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks(){return allTasks;}
    public static void insert(Task task){taskDatabaseRepository.insert(task);}
    public static void update(Task task){taskDatabaseRepository.update(task);}
    public void delete(Task task){taskDatabaseRepository.delete(task);}
    public LiveData<Task> get(long id){return taskDatabaseRepository.get(id);}

}
