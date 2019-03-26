package com.precious.task.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.precious.task.BR;
import com.precious.task.R;
import com.precious.task.databinding.TaskListBinding;
import com.precious.task.model.Task;
import com.precious.task.model.ViewTask;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TaskViewHolder> {
    Context context;
    List<Task> taskList;

    public RecyclerViewAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        TaskListBinding taskListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.task_list,
                viewGroup, false );

        return new TaskViewHolder(taskListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

        Task task = taskList.get(i);

        taskViewHolder.taskListBinding.setTask(task);

        taskViewHolder.taskListBinding.setViewTask((ViewTask) context);

        taskViewHolder.taskListBinding.setVariable(BR.task, task);

        taskViewHolder.taskListBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        if(taskList != null){
            return taskList.size();
        }
        return 0;
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public List<Task> getTaskList(){
        return taskList;
    }

    public Task getTaskAtId(int adapterPosition) {

        return taskList.get(adapterPosition);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

         TaskListBinding taskListBinding;

         public TaskViewHolder(@NonNull View itemView) {
             super(itemView);
             taskListBinding = DataBindingUtil.getBinding(itemView);
         }
     }
}
