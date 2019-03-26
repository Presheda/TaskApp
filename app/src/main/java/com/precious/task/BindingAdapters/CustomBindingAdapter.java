package com.precious.task.BindingAdapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.TextView;

//import com.precious.task.Adapter.RecyclerViewAdapter;
import com.precious.task.Adapter.RecyclerViewAdapter;
import com.precious.task.R;
import com.precious.task.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class CustomBindingAdapter {



    @BindingAdapter("setRadioButtonValue")
    public static void setRadioButton(RadioButton radioButton, int taskPriority){
      if(radioButton != null){
          switch (taskPriority){
              case 1:
                  if(radioButton.getId() == R.id.priorityHigh){
                      radioButton.setChecked(true);
                  }
              case 2:
                  if(radioButton.getId() == R.id.priorityMedium){
                      radioButton.setChecked(true);
                  }

              case 3:
                  if(radioButton.getId() == R.id.priorityLow){
                      radioButton.setChecked(true);
                  }
          }


          Context mContext = radioButton.getContext();

          int priorityColor = 0;

          switch (radioButton.getId()) {
              case R.id.priorityHigh:
                  priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                  break;
              case R.id.priorityMedium:
                  priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                  break;
              case R.id.priorityLow:
                  priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                  break;
              default:
                  break;
          }

          radioButton.setBackgroundColor(priorityColor);
       }


    }


    @BindingAdapter("setAdapter")
        public static void setRecyclerViewadapter(RecyclerView recyclerView, List<Task> taskList){

        if(recyclerView != null){
            if(taskList != null) {

                RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerView.getAdapter();

                if (adapter == null) {

                     adapter = new RecyclerViewAdapter(recyclerView.getContext(), taskList);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());

//                    DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), VERTICAL);
//                    recyclerView.addItemDecoration(decoration);


                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);
                }

                adapter.setTaskList(taskList);
            }
        }

    }

    @BindingAdapter("setDate")
    public static void setDateText(TextView textView, Date date){

        String DATE_FORMAT = "dd/MM/yyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        if(date != null){
            textView.setText(dateFormat.format(date));
        }
    }


    @BindingAdapter("setPriorityColor")
    public static void getPriorityColor(TextView textView, int priority) {

        Context mContext = textView.getContext();

        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }


        GradientDrawable priorityCircle = (GradientDrawable) textView.getBackground();
        // Get the appropriate background color based on the priority
        priorityCircle.setColor(priorityColor);

        textView.setText(String.valueOf(priority));

    }

}
