package com.alsahmou.focusapp;

/*Class to construct tasks by putting task icons and names together*/
public class TaskConstructor {

    private String mTaskName;

    public TaskConstructor(String text){

        mTaskName = text;
    }

    public String getTaskName() {
        return mTaskName;
    }
}


