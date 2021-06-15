package com.girish.todoister.adapter;

import com.girish.todoister.model.Task;

public interface OnTaskClickListener {
    void onTaskClick(Task task);
    void onRadioButtonClick(Task task);
}
