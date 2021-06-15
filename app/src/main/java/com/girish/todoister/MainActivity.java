package com.girish.todoister;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.girish.todoister.adapter.OnTaskClickListener;
import com.girish.todoister.adapter.RecyclerViewAdapter;
import com.girish.todoister.databinding.ActivityMainBinding;
import com.girish.todoister.model.SharedViewModel;
import com.girish.todoister.model.Task;
import com.girish.todoister.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener {
    private TaskViewModel taskViewModel;
    private ActivityMainBinding binding;
    private BottomSheetFragment bottomSheetFragment;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior =
                BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN, false);


        taskViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(MainActivity.this.getApplication())
                .create(TaskViewModel.class);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        binding.fab.setOnClickListener(view -> showBottomSheet());

        // RecyclerView
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        taskViewModel.getAllTasks().observe(this, tasks -> {
            Instant now = Instant.now();
            for(Task task: tasks){
                if(task!=null && task.dateDue.compareTo(Date.from(now.minus(1, ChronoUnit.DAYS))) < 0){
                    taskViewModel.delete(task);
                }
            }
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
            binding.recyclerView.setAdapter(recyclerViewAdapter);
        });


    }

    private void showBottomSheet(){
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskClick(Task task) {
        sharedViewModel.selectItem(task);
        showBottomSheet();
    }

    @Override
    public void onRadioButtonClick(Task task) {
        taskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}