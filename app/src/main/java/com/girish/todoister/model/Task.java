package com.girish.todoister.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.girish.todoister.util.Priority;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {
    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    public String task;

    public Priority priority;

    @ColumnInfo(name="due_date")
    public Date dateDue;

    @ColumnInfo(name="created_at")
    public Date dateCreated;

    @ColumnInfo(name="is_done")
    public boolean isDone;

    public Task(String task, Priority priority, Date dateDue, Date dateCreated, boolean isDone) {
        this.task = task;
        this.priority = priority;
        this.dateDue = dateDue;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
