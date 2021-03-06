package com.alsahmou.focusapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class TaskManagerAdapter extends RecyclerView.Adapter<TaskManagerAdapter.TaskViewHolder> {

    private ArrayList<TaskConstructor> mTasksList;

    private OnItemClickListener mListener;

    /*Finds the position of the task clicked in the array*/
    public interface OnItemClickListener{

        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    /*Sets a listener to be used in other functions*/
    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    /*Creates the view of the recycler with the tasks names, images and deleted button*/
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public ImageView mTaskIconImage;
        public TextView mTaskNameTextView;
        public ImageView mDeleteTaskImageView;

        public TaskViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTaskIconImage = itemView.findViewById(R.id.taskIconImageView);
            mTaskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            mDeleteTaskImageView = itemView.findViewById(R.id.deleteTaskImageView);

            /*Find the position of the item to be deleted after the delete button has been clicked*/
            mDeleteTaskImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){

                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }

    /*Creates an instance of the tasks list to be used in the recycler view*/
    public TaskManagerAdapter(ArrayList<TaskConstructor> exampleList) {
        mTasksList = exampleList;
    }

    /*Passes the layout of a task to the adapter*/
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_constructor, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(v, mListener);
        return taskViewHolder;
    }

    /*Assigns values of the task name and task icon to TaskConstructor*/
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        List<Integer> dynamicIcons;
        dynamicIcons = Constants.TASKS_ICONS;

        TaskConstructor currentItem = mTasksList.get(position);
        /*holder.mTaskIconImage.setImageResource(Constants.TASKS_ICONS[position]);*/
        holder.mTaskIconImage.setImageResource(dynamicIcons.get(position));
        holder.mTaskNameTextView.setText(currentItem.getTaskName());

    }

    /*Returns the size of the array*/
    @Override
    public int getItemCount() {
        return mTasksList.size();
    }
}


