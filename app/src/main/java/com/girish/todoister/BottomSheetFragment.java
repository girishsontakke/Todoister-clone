package com.girish.todoister;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.girish.todoister.databinding.BottomSheetBinding;
import com.girish.todoister.model.SharedViewModel;
import com.girish.todoister.model.Task;
import com.girish.todoister.model.TaskViewModel;
import com.girish.todoister.util.Priority;
import com.girish.todoister.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetBinding binding;
    private Date dueDate = Calendar.getInstance().getTime();
    private SharedViewModel sharedViewModel;
    private boolean isEdit = false;
    private Task task;
    private Priority priority = Priority.HIGH;

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = BottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // set event listeners
        binding.saveTodoButton.setOnClickListener(this::onSave);
        binding.todayCalendarButton.setOnClickListener(this::showCalender);
        binding.priorityTodoButton.setOnClickListener(this::showPriority);
        binding.calendarView.setOnDateChangeListener(this::onSelectedDayChange);
        binding.todayChip.setOnClickListener(this);
        binding.tomorrowChip.setOnClickListener(this);
        binding.nextWeekChip.setOnClickListener(this);
        binding.radioGroupPriority.setOnCheckedChangeListener(this::onPriorityChange);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null) {
            task = sharedViewModel.getSelectedItem().getValue();
            isEdit = true;
            dueDate = task.dateDue;
            priority = task.priority;
            binding.radioGroupPriority.check(Utils.priorityToId(priority));
            binding.enterTodoEt.setText(task.getTask());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        sharedViewModel.selectItem(null);
        binding.radioButtonHigh.setChecked(true);
        binding.enterTodoEt.setText("");
        dueDate = Calendar.getInstance().getTime();
        priority = Priority.HIGH;
    }

    @SuppressLint("NonConstantResourceId")
    private void onPriorityChange(RadioGroup group, int checkedId) {
        if (group.getVisibility() == View.VISIBLE) {
            switch (checkedId) {
                case R.id.radioButton_high:
                    priority = Priority.HIGH;
                    break;
                case R.id.radioButton_med:
                    priority = Priority.MEDIUM;
                    break;
                case R.id.radioButton_low:
                    priority = Priority.LOW;
                    break;
                default:
                    break;
            }
        } else {
            priority = Priority.HIGH;
        }
    }

    private void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, dayOfMonth);
        dueDate = calendar.getTime();
    }

    private void showCalender(View view) {
        Utils.hideSoftSoftware(view);
        if (binding.calendarGroup.getVisibility() == View.GONE) {
            binding.calendarGroup.setVisibility(View.VISIBLE);
        } else {
            binding.calendarGroup.setVisibility(View.GONE);
        }
    }

    private void showPriority(View view) {
        Utils.hideSoftSoftware(view);
        if (binding.radioGroupPriority.getVisibility() == View.GONE) {
            binding.radioGroupPriority.setVisibility(View.VISIBLE);
        } else {
            binding.radioGroupPriority.setVisibility(View.GONE);
        }
    }

    private void onSave(View view) {
        String text = binding.enterTodoEt.getText().toString().trim();
        if (!TextUtils.isEmpty(text) && dueDate!=null && priority != null) {
            if (isEdit) {
                task.setTask(text);
                task.setDateDue(dueDate);
                task.setPriority(priority);
                TaskViewModel.update(task);
                isEdit = false;
            } else {
                task = new Task(text, priority, dueDate,
                        Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(task);
            }
        } else {
            Toast.makeText(getContext(), "todo can't be empty", Toast.LENGTH_SHORT).show();
        }
        Utils.hideSoftSoftware(view);
        if (this.isVisible()) this.dismiss();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Calendar calendar = Calendar.getInstance();
        switch (id) {
            case R.id.today_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                break;
            case R.id.tomorrow_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                break;
            case R.id.next_week_chip:
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                break;
            default:
                break;
        }
        dueDate = calendar.getTime();
    }
}