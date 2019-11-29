package com.alsahmou.focusapp;

import java.util.Arrays;
import java.util.List;

public class Constants {

    /* A list including available task icons for usage by users, ordered to ensure that each icon is unique to each task name*/
    public static int[] TASKS_ICONS = new int[]{R.drawable.purple_icon, R.drawable.red, R.drawable.dark_blue_icon, R.drawable.yellow_icon};

    /* A list including motivational texts to be shown to the user while the timer is running*/
    final static List<String> MOTIVATION_TEXTS = Arrays.asList("Leave me alone!", "Focus!", "Don't look at me!", "Almost there!", "Get back to work!");

    /* Default timer value at the start of the application*/
    public static long DEFAULT_TIMER_VALUE = 25 * 60 * 1000;

}
