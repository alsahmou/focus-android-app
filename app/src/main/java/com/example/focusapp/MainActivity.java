package com.example.focusapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private EditText mTimeEditText;

    private TextView mTimerTextView;
    private TextView mMotivateTextView;
    private TextView mTotalTextView;
    private TextView mSecondaryTextView;

    private Button mStartBtn;
    private Button mResetBtn;
    private Button mSetBtn;
    private Button mTaskManagerBtn;

    private boolean mTimerRunning;
    private boolean mChronoRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private long chronometerTime = 0;

    private int totalTime = 0;
    private int i = 0;

    private CountDownTimer mCountDownTimer;
    private Chronometer chronometer;

    final Handler handler = new Handler();

    List<String> motivationTexts = Arrays.asList("Leave me alone!", "Focus!", "Don't look at me!", "Almost there!", "Get back to work!");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeEditText = findViewById(R.id.timeEditText);

        mTimerTextView = findViewById(R.id.timerTextView);
        mTotalTextView = findViewById(R.id.totalTextView);
        mMotivateTextView = findViewById(R.id.motivateTextView);
        mSecondaryTextView = findViewById(R.id.secondaryTextView);

        mStartBtn = findViewById(R.id.startBtn);
        mResetBtn = findViewById(R.id.resetBtn);
        mSetBtn = findViewById(R.id.setBtn);
        mTaskManagerBtn = findViewById(R.id.taskManagerBtn);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Extra Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        mTimerRunning = false;
        mChronoRunning = false;

        /*Default value of the timer, set at 25 minutes*/
        mTimeLeftInMillis = 1500000;


        /*Button redirects the user to the Task Manager App, the intent is set as the taskManagerIntent then called by startActivity method */
        mTaskManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent taskManagerIntent = new Intent(getApplicationContext(), TaskManager.class);
                startActivity(taskManagerIntent);
            }
        });

        /*Button allows the user to set the time for the timer*/
        mSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeInput = mTimeEditText.getText().toString();

                /*If the input is empty, it returns an error to the user and deny them from setting the time*/
                if (timeInput.length() == 0){
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Changes the input in milliseconds to minutes*/
                long millisInput = Long.parseLong(timeInput) * 60000;

                /*If the input is 0, it returns an error to the user and deny them from setting the time, negative values are not allowed to be entered by the EditText view*/
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                /*Resets the EdtiText'v view*/
                mTimeEditText.setText("");
            }
        });

        /*Button allows the user to start the timer*/
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        /*Button allows the user to reset the timer*/
        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometerTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                updateTotalTime();
                resetTimer();
            }
        });

        /*Update both timer and interface onCreate*/
        updateCountDownText();
        updateInterface();

    }



    /*Set time function to set the start time of the timer in milliseconds*/
    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    /*Start time function to start the timer once called
    Timer end time is set by adding the system's current time to the set time
     */
    private void startTimer() {
        closeKeyboard();
        mTimeLeftInMillis = mStartTimeInMillis;
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        /*onTick updates the timer and timer text every 100 milliseconds intervals*/
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                mTimerRunning = true;
                mTimeLeftInMillis = millisUntilFinished;
                mChronoRunning = false;
                updateCountDownText();
            }
            /*Once the timer finishes the chronometer starts and sets its base time to the system's current time*/
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mChronoRunning = true;
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                updateInterface();

            }
        }.start();

        mTimerRunning = true;
        updateInterface();
    }

    /*Reset timer function to stop the timer from running at any given time*/
    private void resetTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mTimerRunning = false;
        mChronoRunning = false;
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        chronometer.stop();

        updateInterface();
    }

    /*Function to update the timer's text in seconds, minutes and hours*/
    private void updateCountDownText() {

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTimerTextView.setText(timeLeftFormatted);
    }

    /*Function to update the total time used by the user on the app*/
    private void updateTotalTime() {

        if (!mTimerRunning) {
            int seconds = (int) ((mStartTimeInMillis + chronometerTime) / 1000);
            totalTime+= seconds;

            mTotalTextView.setText(totalTime+"Seconds");
        }

    }

    /*Function to close the keyboard, to be used once the user is done inputting into the app*/
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*Function to update the interface during various states of the app usage*/
    private void updateInterface() {

        if (mTimerRunning) {

            /*If the timer is running but the chronometer has not started running yet*/
            if (!mChronoRunning) {
                mTimeEditText.setVisibility(View.INVISIBLE);
                mTimerTextView.setVisibility(View.VISIBLE);
                mTotalTextView.setVisibility(View.INVISIBLE);
                mMotivateTextView.setVisibility(View.VISIBLE);
                mStartBtn.setVisibility(View.INVISIBLE);
                mResetBtn.setVisibility(View.VISIBLE);
                mSetBtn.setVisibility(View.INVISIBLE);
                mSecondaryTextView.setVisibility(View.INVISIBLE);
                mMotivateTextView.setTextColor(Color.RED);
                mResetBtn.setText("GIVE UP");
                mMotivateTextView.setText(""+motivationTexts.get(i));

                /*Handler used to update the texts by looping through an arraylist*/
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        mMotivateTextView.setText(""+motivationTexts.get(i));
                        i++;
                        if(i < motivationTexts.size()) {
                            handler.postDelayed(this, 10000);
                        }
                        else {
                            i = 0;
                            handler.postDelayed(this, 10000);
                        }
                    }

                });
            }
        }

        else {
            handler.removeCallbacksAndMessages(null);
            /*If the timer is not running and the chronometer is running*/
            if (mChronoRunning) {
                mTimeEditText.setVisibility(View.INVISIBLE);
                mTimerTextView.setVisibility(View.VISIBLE);
                mTotalTextView.setVisibility(View.INVISIBLE);
                mMotivateTextView.setVisibility(View.VISIBLE);
                mMotivateTextView.setText("Good Job!!");
                mMotivateTextView.setTextColor(Color.BLUE);
                mStartBtn.setVisibility(View.INVISIBLE);
                mResetBtn.setVisibility(View.VISIBLE);
                mResetBtn.setText("STOP");
                mSetBtn.setVisibility(View.INVISIBLE);
                mSecondaryTextView.setVisibility(View.INVISIBLE);


            }
            /*If both timer and chronometer are not running*/
            else {

                mTimeEditText.setVisibility(View.VISIBLE);
                mTimerTextView.setVisibility(View.VISIBLE);
                mTotalTextView.setVisibility(View.VISIBLE);
                mMotivateTextView.setVisibility(View.INVISIBLE);
                mStartBtn.setVisibility(View.VISIBLE);
                mResetBtn.setVisibility(View.INVISIBLE);
                mSetBtn.setVisibility(View.VISIBLE);
                mSecondaryTextView.setVisibility(View.VISIBLE);
                chronometer.setText("00:00");

            }
        }


    }

    /*Function to kill the app if the user exists the application*/
    protected void onPause() {
        super.onPause();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}



