package com.example.focusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class taskAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] tasksList;
    String[] tasksIconsList;

    public taskAdapter(Context c, String[] task, String[] icon) {
        tasksList = task;
        tasksIconsList = icon;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasksList.length;
    }

    @Override
    public Object getItem(int i) {
        return tasksList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.tasks_list_view_detail, null);
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        TextView taskIconTextView = (TextView) v.findViewById(R.id.taskIconTextView);

        String task = tasksList[i];
        String icon = tasksIconsList[i];

        taskTextView.setText(task);
        taskIconTextView.setText(icon);

        return v;
    }
}
