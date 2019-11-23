package com.example.focusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;


public class TaskManager extends ListActivity {
    /*
    ListView tasksListView;
    String[] tasksList;
    String[] tasksIconsList;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Task manager open");
        setContentView(R.layout.activity_task_manager);

        Context context = getApplicationContext();
        Resources resources = context.getResources();

        String[] tasks_list = {"Code", "French", "Study", "Tasks"};


        TypedArray task_icons = resources.obtainTypedArray(R.array.task_icons);



        setListAdapter(new ImageAndTextAdapter(context, R.layout.secondary_layout, tasks_list, task_icons));

        /*Resources resources = getResources();

        tasksListView = findViewById(R.id.tasksListView);
        tasksList = resources.getStringArray(R.array.tasksList);
        tasksIconsList = resources.getStringArray(R.array.tasksIconsList);

        taskAdapter taskAdapter = new taskAdapter(this, tasksList, tasksIconsList);
        tasksListView.setAdapter(taskAdapter);*/


    }
}
