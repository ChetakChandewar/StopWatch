package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** @noinspection ALL*/
public class LapAdapter extends RecyclerView.Adapter<LapAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Long> lapTimes;
    private int lapNumber;

    public LapAdapter(Context context, ArrayList<Long> lapTimes) {
        this.context = context;
        this.lapTimes = lapTimes;
        this.lapNumber = lapTimes.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lap_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        long lapTime = lapTimes.get(position);
        holder.lapTimeTextView.setText(formatTime(lapTime));

        // Set lap number in regular order (1, 2, 3, ...)
        int displayedLapNumber = lapNumber - position;
        holder.lapNumberTextView.setText(String.valueOf("Lap " + displayedLapNumber + "." ));

    }

    @Override
    public int getItemCount() {
        return lapTimes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lapNumberTextView;
        TextView lapTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lapNumberTextView = itemView.findViewById(R.id.lapNumberTextView);
            lapTimeTextView = itemView.findViewById(R.id.lapTimeTextView);
        }
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(long time) {
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

        return timeFormatted;
    }

    public void incrementLapNumber() {
        lapNumber++;
    }
}
