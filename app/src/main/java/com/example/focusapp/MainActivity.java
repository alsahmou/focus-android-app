package com.example.focusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.os.*;
import android.widget.Toast;

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

    private boolean mTimerRunning;
    private boolean hasFinished;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private int totalTime = 0;
    private int i = 0;
    private long chronometerTime = 0;

    private CountDownTimer mCountDownTimer;
    private Chronometer chronometer;

    private boolean running;

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

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Extra Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        /*if (savedInstanceState != null ){
            hasFinished = savedInstanceState.getBoolean("hasFinished");

        }
        System.out.println("Has finished in on create"+hasFinished);*/

        mTimerRunning = false;
        hasFinished = false;

        /*mStartTimeInMillis = 1500000;*/
        mTimeLeftInMillis = 1500000;





        mSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeInput = mTimeEditText.getText().toString();
                if (timeInput.length() == 0){
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(timeInput) * 60000;

                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                mTimeEditText.setText("");
            }
        });

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometerTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                System.out.println("Stopwatch time" + chronometerTime);
                updateTotalTime();
                resetTimer();
            }
        });

        updateCountDownText();
        updateInterface();

    }




    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }


    private void startTimer() {

        closeKeyboard();

        mTimeLeftInMillis = mStartTimeInMillis;
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                mTimerRunning = true;
                mTimeLeftInMillis = millisUntilFinished;
                hasFinished = false;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                hasFinished = true;
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                updateInterface();

            }
        }.start();

        mTimerRunning = true;
        updateInterface();
    }

    private void resetTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        /*mCountDownTimer.cancel();*/
        mTimerRunning = false;
        hasFinished = false;
        System.out.println("Timerrunning?"+ mTimerRunning);
        System.out.println("mStartTimeinMillis in reset function is"+ mStartTimeInMillis);
        mTimeLeftInMillis = mStartTimeInMillis;
        System.out.println("mTimeLeftinmillis in reset function is"+ mTimeLeftInMillis);
        updateCountDownText();
        chronometer.stop();

        updateInterface();
    }

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

    private void updateTotalTime() {

        if (!mTimerRunning) {
            System.out.println("Stopwatch time in update total" + chronometerTime);
            int seconds = (int) ((mStartTimeInMillis + chronometerTime) / 1000);
            totalTime+= seconds;

            mTotalTextView.setText(totalTime+"Seconds");
        }



    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void updateInterface() {
        if (mTimerRunning) {
            if (!hasFinished) {

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
                            System.out.println(i);
                        }
                    }

                });
            }
        }

        else {
            handler.removeCallbacksAndMessages(null);
            if (hasFinished) {
                System.out.println("Has finished inside update interface");
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

    /* OLD USER INTERFACE private void updateInterface() {

        if (mTimerRunning) {
            mTimeEditText.setVisibility(View.INVISIBLE);

            mTotalTextView.setVisibility(View.INVISIBLE);
            mMotivateTextView.setVisibility(View.VISIBLE);
            mMotivateTextView.setText(""+motivationTexts.get(i));
            mMotivateTextView.setTextColor(Color.RED);

            mSetBtn.setVisibility(View.INVISIBLE);
            mStartBtn.setVisibility(View.INVISIBLE);
            mResetBtn.setVisibility(View.VISIBLE);
            mResetBtn.setText("GIVE UP");


            handler.post(new Runnable(){
                @Override
                public void run() {
                    mMotivateTextView.setText(""+motivationTexts.get(i));
                    i++;
                    if(i < motivationTexts.size()) {
                        handler.postDelayed(this, 1000);
                    }
                    else {
                        i = 0;
                        handler.postDelayed(this, 1000);
                        System.out.println(i);
                    }
                }

            });
        }

        else {
            mTimeEditText.setVisibility(View.VISIBLE);

            mTotalTextView.setVisibility(View.VISIBLE);
            mMotivateTextView.setVisibility(View.INVISIBLE);

            mStartBtn.setVisibility(View.VISIBLE);
            mResetBtn.setVisibility(View.INVISIBLE);
            mSetBtn.setVisibility(View.VISIBLE);

            handler.removeCallbacksAndMessages(null);

            if (hasFinished) {
                mTotalTextView.setVisibility(View.INVISIBLE);
                mMotivateTextView.setVisibility(View.VISIBLE);
                mMotivateTextView.setText("Good Job!!");
                mMotivateTextView.setTextColor(Color.BLUE);


                mStartBtn.setVisibility(View.INVISIBLE);
                mResetBtn.setVisibility(View.VISIBLE);
                mResetBtn.setText("Stop");
            }
        }
    }*/


    /*Not sure if I need the next 2 blocks or not, investigate after fixing count down timer*/



   /* @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putBoolean("hasFinished", hasFinished);
        outState.putLong("endTime", mEndTime);



    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        System.out.println("Has finished in restore state"+hasFinished);
        System.out.println("timer running in restore state"+mTimerRunning);
        updateCountDownText();
        updateInterface();

        if (mTimerRunning) {
            mEndTime = savedInstanceState.getLong("mEndTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }

    }*/

    protected void onPause() {
        super.onPause();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}



