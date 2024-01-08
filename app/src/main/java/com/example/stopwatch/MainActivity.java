package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView stopwatchTextView;
    private RecyclerView lapsRecyclerView;
    private LapAdapter lapAdapter;
    private Handler handler;
    private boolean isRunning;
    private long startTime, elapsedTime, pausedTime = 0L;
    private ArrayList<Long> lapTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchTextView = findViewById(R.id.stopwatchTextView);
        lapsRecyclerView = findViewById(R.id.lapsRecyclerView);
        handler = new Handler();
        lapTimes = new ArrayList<>();

        lapAdapter = new LapAdapter(this, lapTimes);
        lapsRecyclerView.setAdapter(lapAdapter);
        lapsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void toggleStartPause(View view) {
        if (isRunning) {
            pauseStopwatch(view);
        } else {
            startStopwatch(view);
        }
    }

    public void startStopwatch(View view) {
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis() - pausedTime;
            handler.postDelayed(runnable, 0);
            isRunning = true;

            // Show the "Pause" button and hide the "Start" button
            ImageButton startButton = findViewById(R.id.startButton);
            ImageButton pauseButton = findViewById(R.id.pauseButton);
            startButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        }
    }

    public void pauseStopwatch(View view) {
        if (isRunning) {
            handler.removeCallbacks(runnable);
            pausedTime = SystemClock.uptimeMillis() - startTime;
            isRunning = false;

            // Show the "Start" button and hide the "Pause" button
            ImageButton startButton = findViewById(R.id.startButton);
            ImageButton pauseButton = findViewById(R.id.pauseButton);
            startButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetStopwatch(View view) {
        if (!isRunning) {
            startTime = 0L;
            elapsedTime = 0L;
            pausedTime = 0L;
            lapTimes.clear();
            displayTime(elapsedTime);
            lapAdapter.notifyDataSetChanged();
        }
    }

    public void lapStopwatch(View view) {
        if (isRunning) {
            // Record the lap time at the moment the lap button is pressed
            long currentLapTime = elapsedTime;
            lapTimes.add(0, currentLapTime); // Add the lap time at the beginning of the list
            lapAdapter.incrementLapNumber();
            lapAdapter.notifyItemInserted(0);

            // Scroll to the first item in the RecyclerView
            lapsRecyclerView.scrollToPosition(0);
        }
    }





    private final Runnable runnable = new Runnable() {
        public void run() {
            elapsedTime = SystemClock.uptimeMillis() - startTime;
            displayTime(elapsedTime);
            handler.postDelayed(this, 30);
        }
    };

    @SuppressLint("DefaultLocale")
    private void displayTime(long time) {
        int hours = (int) (time / 3600000);
        int minutes = (int) (time % 3600000 / 60000);
        int seconds = (int) (time % 60000 / 1000);
        int milliseconds = (int) (time % 1000);

        String timeFormatted;
        if (hours > 0) {
            timeFormatted = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
        } else {
            timeFormatted = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
        }

        stopwatchTextView.setText(timeFormatted);
    }
}
