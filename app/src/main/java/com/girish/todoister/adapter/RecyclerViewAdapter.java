package com.girish.todoister.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.girish.todoister.R;
import com.girish.todoister.databinding.TodoRowBinding;
import com.girish.todoister.model.Task;
import com.girish.todoister.util.Utils;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> allTasks;
    public final OnTaskClickListener taskClickListener;

    public RecyclerViewAdapter(List<Task> allTasks, OnTaskClickListener onTaskClickListener) {
        this.allTasks = allTasks;
        this.taskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoRowBinding todoRowBinding = TodoRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new ViewHolder(todoRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = allTasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TodoRowBinding viewHolderBinding;
        public OnTaskClickListener onTaskClickListener;

        public ViewHolder(@NonNull TodoRowBinding todoRowBinding) {
            super(todoRowBinding.getRoot());
            viewHolderBinding = todoRowBinding;
            onTaskClickListener = taskClickListener;
            todoRowBinding.getRoot().setOnClickListener(this);
            todoRowBinding.todoRadioButton.setOnClickListener(this);
        }

        public void bind(Task task) {
            viewHolderBinding.todoRowTodo.setText(task.task);
            viewHolderBinding.todoRowChip.setText(Utils.getFormattedDate(task.dateDue));
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Task task = allTasks.get(getAdapterPosition());
            if(id == R.id.todo_row_layout){
                onTaskClickListener.onTaskClick(task);
            }else if(id == R.id.todo_radio_button){
                onTaskClickListener.onRadioButtonClick(task);
            }
        }
    }
}
