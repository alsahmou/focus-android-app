package com.example.focusapp;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskManager extends AppCompatActivity {

    private ArrayList<TaskConstructor> mTasksList;

    private RecyclerView mRecyclerView;
    private TaskManagerAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private Button mInsertBtn;

    private EditText mInsertEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        createTasksList();
        buildRecyclerView();
        setButtons();
    }

    /*Inserts an item into the array*/
    public void addTask(String input) {

        mTasksList.add(new TaskConstructor(input));
        mAdapter.notifyItemInserted(mTasksList.size());
        closeKeyboard();
    }

   /*Closes the keyboard, to be used after the user inputs into the app*/
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*Deletes a task from the tasks list*/
    public void deleteTask (int position){

        mTasksList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /*Initializes the tasks lists with default values*/
    public void createTasksList() {

        mTasksList = new ArrayList<>();

        mTasksList.add(new TaskConstructor("Line1"));

    }

    /*Builds the recycler view*/
    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TaskManagerAdapter(mTasksList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TaskManagerAdapter.OnItemClickListener() {
            /*OnClick method, not used but required by the setOnItemClickListner*/
            @Override
            public void onItemClick(int position) {
            }

            /*Deletes tasks from their position in the array*/
            @Override
            public void onDeleteClick(int position) {
                deleteTask(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
    }

    /*Controls the buttons functionalities*/
    public void setButtons(){
        mInsertBtn = findViewById(R.id.addTaskBtn);

        mInsertEditText = findViewById(R.id.addTaskEditText);

        mInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getInput = mInsertEditText.getText().toString();
                /*If there is no input in the EditText field*/
                if (getInput.length() == 0) {
                    Toast.makeText(TaskManager.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*If the user has reached the max amount of tasks allowed*/
                if (mTasksList.size() == Constants.taskIconsList.length){
                    Toast.makeText(TaskManager.this, "Max amount of tasks reached", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    addTask(getInput);
                }
                /*rests the edit text field after the user inputs*/
                mInsertEditText.setText("");

            }

        });
    }
}






/*private Button buttonRemove;*/

/*private EditText editTextRemove;*/


      /*final Context context = getApplicationContext();
        Resources resources = context.getResources();
        final TypedArray task_icons = resources.obtainTypedArray(R.array.task_icons);*/



/*changeItem(position,"Clicked");*/

  /*public void changeItem(int position, String text) {
        mTasksList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);


    }*/

/*buttonRemove = findViewById(R.id.button_remove);*/

/*editTextRemove = findViewById(R.id.edittext_remove);*/

/*buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = Integer.parseInt(editTextRemove.getText().toString());
                mTasksList.remove(position);

            }
        });*/