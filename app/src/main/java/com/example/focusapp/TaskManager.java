package com.example.focusapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class TaskManager extends ListActivity {
    /*
    ListView tasksListView;
    String[] tasksList;
    String[] tasksIconsList;*/

    private Button mCreateBtn;
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Task manager open");
        setContentView(R.layout.activity_task_manager);

        final Context context = getApplicationContext();
        Resources resources = context.getResources();

        /*
        final String[] tasks_list = {"Code", "French", "Study"};*/

        final ArrayList<String> obj = new ArrayList<String>(
                Arrays.asList("Tasks", "French", "Code"));

        System.out.println("array list" + obj);


        final TypedArray task_icons = resources.obtainTypedArray(R.array.task_icons);



        setListAdapter(new ImageAndTextAdapter(context, R.layout.secondary_layout, obj, task_icons));

        mCreateBtn = findViewById(R.id.createBtn);
        mEditText = findViewById(R.id.EditText);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getInput = mEditText.getText().toString();


                if (getInput == null) {
                    Toast.makeText(TaskManager.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                }

                if (obj.size() == task_icons.length() ){
                    Toast.makeText(TaskManager.this, "Max amount of tasks reached", Toast.LENGTH_SHORT).show();
                }

                else {
                    obj.add(getInput);
                    setListAdapter(new ImageAndTextAdapter(context, R.layout.secondary_layout, obj, task_icons));
                }

            }
        });


        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                obj.remove(ImageAndTextAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView();*/


        /*Resources resources = getResources();

        tasksListView = findViewById(R.id.tasksListView);
        tasksList = resources.getStringArray(R.array.tasksList);
        tasksIconsList = resources.getStringArray(R.array.tasksIconsList);

        taskAdapter taskAdapter = new taskAdapter(this, tasksList, tasksIconsList);
        tasksListView.setAdapter(taskAdapter);*/


    }
}
