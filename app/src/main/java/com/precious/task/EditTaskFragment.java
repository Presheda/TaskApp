package com.precious.task;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.precious.task.databinding.EditTaskFragmentBinding;
import com.precious.task.model.AppExecutors;
import com.precious.task.model.Task;
import com.precious.task.model.TaskViewModel;

import java.util.Date;

public class EditTaskFragment extends DialogFragment {

    EditTaskFragmentBinding editTaskFragmentBinding;
    private int task_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        editTaskFragmentBinding = EditTaskFragmentBinding.inflate(inflater);


        setError();

        editTaskFragmentBinding.button.setOnClickListener((view) -> {
            savePet();
        });

        if (task_id == -1) {

        } else {
            loadTaskByid(task_id);
        }

        return editTaskFragmentBinding.getRoot();
    }

    private void savePet() {


        String taskName = editTaskFragmentBinding.addTaskEdit.getText().toString().trim();
        int checkedRadiobutton = getCheckedRadioButton();
        if (!taskName.isEmpty()) {

            Date dateCreated = new Date();
            Task task = new Task(taskName, dateCreated, checkedRadiobutton);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {

                    TaskViewModel viewModel = ViewModelProviders.of(EditTaskFragment.this).get(TaskViewModel.class);

                    if (task_id == -1) {

                        viewModel.insertTask(task);
                    } else {
                        task.setId(task_id);

                        viewModel.updateTask(task);

                    }
                }
            });

            dismiss();
        }




    }

    public int getCheckedRadioButton() {

        int checkedRadiobutton;

        editTaskFragmentBinding.priorityLayout.getCheckedRadioButtonId();
        checkedRadiobutton = editTaskFragmentBinding.priorityLayout.getCheckedRadioButtonId();

        switch (checkedRadiobutton) {
            case R.id.priorityHigh:
                checkedRadiobutton = 1;
                break;

            case R.id.priorityMedium:
                checkedRadiobutton = 2;

                break;

            case R.id.priorityLow:
                checkedRadiobutton = 3;
            default:
                checkedRadiobutton = 3;

        }


        return checkedRadiobutton;

    }


    @Override
    public void onStart() {

        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            task_id = bundle.getInt(MainActivity.TASKID);
        } else {
            task_id = -1;
        }


    }

    private void loadTaskByid(int task_id) {

        TaskViewModel viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                viewModel.getTaskById(task_id).observe(EditTaskFragment.this, new Observer<Task>() {
                    @Override
                    public void onChanged(@Nullable Task task) {
                        initializeTask(task);
                    }
                });
            }
        });


    }

    private void initializeTask(Task task) {
        editTaskFragmentBinding.addTaskEdit.setText(task.getTaskTitle());
        editTaskFragmentBinding.priorityLayout.check(determineCheckedId(task.getTaskPriority()));
    }

    private int determineCheckedId(int taskPriority) {
        int checked = 0;

        switch (taskPriority) {
            case 1:
                checked = R.id.priorityHigh;
                break;
            case 2:
                checked = R.id.priorityMedium;
                break;
            case 3:
                checked = R.id.priorityLow;
                break;
        }

        return checked;
    }


    public boolean setError() {


        editTaskFragmentBinding.addTaskEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().isEmpty()) {

                    editTaskFragmentBinding.addTaskLayout.setErrorEnabled(true);
                    editTaskFragmentBinding.addTaskLayout.setError("Field cannot be empty");
                } else {
                    editTaskFragmentBinding.addTaskLayout.setErrorEnabled(false);
                    editTaskFragmentBinding.addTaskLayout.setError(null);
                }
            }
        });


        return editTaskFragmentBinding.addTaskLayout.isErrorEnabled();

    }
}
