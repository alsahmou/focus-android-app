package com.alsahmou.focusapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleDialog1 extends AppCompatDialogFragment {

    private ArrayList<TaskConstructor> mTasksList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskManagerAdapter mAdapter;
    public String mCurrentTask;
    private ExampleDialogListner listner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        createTasksList();

        mRecyclerView = new RecyclerView(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new TaskManagerAdapter(mTasksList);

        mRecyclerView.setAdapter(mAdapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;

        mAdapter.setOnItemClickListener(new TaskManagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                mCurrentTask = "hi";
                System.out.println("current task is"+mCurrentTask);

            }

            @Override
            public void onDeleteClick(int position) {


                mCurrentTask = mTasksList.get(position).getTaskName();
                System.out.println("current task is"+mCurrentTask);
                /*listner.applyTexts(mCurrentTask);*/


            }
        });


        /*mRecyclerView = getActivity().findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new TaskManagerAdapter(mTasksList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;*/


        /*mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TaskManagerAdapter manager = new TaskManagerAdapter();
        mRecyclerView.setAdapter(manager);*/
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_task_manager, null);

        builder.setView(mRecyclerView)
                .setTitle("Task Chooser")
                .setIcon(R.drawable.red)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listner.applyTexts(mCurrentTask);
                        openDialog();

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (ExampleDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getFragmentManager(), "asdasd");
    }

    public void createTasksList() {

        mTasksList = new ArrayList<>();
        mTasksList.add(new TaskConstructor("Line1"));

    }

    public interface ExampleDialogListner{

        void applyTexts(String task);
    }


}
