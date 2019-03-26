package com.precious.task;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.precious.task.Adapter.RecyclerViewAdapter;
import com.precious.task.databinding.ActivityMainBinding;
import com.precious.task.model.AppExecutors;
import com.precious.task.model.Task;
import com.precious.task.model.TaskViewModel;
import com.precious.task.model.ViewTask;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class MainActivity extends AppCompatActivity implements ViewTask {

    public static final String TASKID = "TASKID";
    ActivityMainBinding mainBinding;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        loadAllTask();

        openEditFragment();

        deleteTask();

    }

    private void deleteTask() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Task task = recyclerViewAdapter.getTaskAtId(viewHolder.getAdapterPosition());

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        TaskViewModel viewModel  = ViewModelProviders.of(MainActivity.this).get(TaskViewModel.class);

                        viewModel.deleteTask(task);
                    }
                });

            }
        }).attachToRecyclerView(mainBinding.recyclerView);
    }

    private void openEditFragment() {
        mainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager  = getSupportFragmentManager();

                EditTaskFragment fragment = new EditTaskFragment();

                fragment.setCancelable(false);

                fragment.show(fragmentManager, "dialog");
            }
        });
    }

    private void loadAllTask() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                TaskViewModel taskViewModel = ViewModelProviders.of(MainActivity.this).get(TaskViewModel.class);

                taskViewModel.getAllTask().observe(MainActivity.this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(@Nullable List<Task> tasks) {
                        populateUi(tasks);
                    }
                });
            }
        });
    }

    private void populateUi(List<Task> tasks) {

        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, tasks);

//
//        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
//        mainBinding.recyclerView.addItemDecoration(decoration);


        mainBinding.recyclerView.setAdapter(recyclerViewAdapter);

        mainBinding.setTaskList(tasks);

    }

    @Override
    public void taskId(int id) {

        FragmentManager fragmentManager  = getSupportFragmentManager();

        EditTaskFragment fragment = new EditTaskFragment();

        fragment.setCancelable(false);



        Bundle bundle  = new Bundle();
        bundle.putInt(TASKID, id);

        fragment.setArguments(bundle);

        fragment.show(fragmentManager, "dialog");







    }
}
