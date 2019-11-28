package com.example.focusapp;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

public class trying extends AppCompatActivity {

    private ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;

    private EditText editTextInsert;
    private EditText editTextRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("trying created");
        setContentView(R.layout.activity_trying);

        createExampleList();
        buildRecyclerView();
        setButtons();

        final Context context = getApplicationContext();
        Resources resources = context.getResources();
        final TypedArray task_icons = resources.obtainTypedArray(R.array.task_icons);



    }



    /*public void insertItem (int position){
        mExampleList.add(position, new ExampleItem(R.drawable.ic_android, "New Item at posotions" + position));
        mAdapter.notifyItemInserted(position);


    }*/

    public void insertItem(String input) {

        mExampleList.add(new ExampleItem(R.drawable.yellow, input));
        mAdapter.notifyItemInserted(mExampleList.size());
        closeKeyboard();
    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            System.out.println("Closing keyboard");
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void removeItem (int position){

        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text) {
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);


    }


    public void createExampleList() {

        mExampleList = new ArrayList<>();

        mExampleList.add(new ExampleItem(R.drawable.ic_android, "Line1"));
        mExampleList.add(new ExampleItem(R.drawable.ic_android1, "Line2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_android2, "Line3"));

    }
    public void buildRecyclerView() {


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListner(new ExampleAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
    }

    public void setButtons(){
        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove = findViewById(R.id.button_remove);

        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);

        /*buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);

            }
        });*/

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getInput = editTextInsert.getText().toString();
                System.out.println("sdasdasd");

                if (getInput.length() == 0) {
                    Toast.makeText(trying.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    System.out.println("No input");
                    return;
                }

                if (mExampleList.size() == Constants.myImageList.length){
                    Toast.makeText(trying.this, "Max amount of tasks reached", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    System.out.println("want to insert item");
                    insertItem(getInput);
                }

            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = Integer.parseInt(editTextRemove.getText().toString());
                mExampleList.remove(position);

            }
        });
    }
}
