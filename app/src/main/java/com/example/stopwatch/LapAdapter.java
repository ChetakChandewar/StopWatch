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

public class LapAdapter extends RecyclerView.Adapter<LapAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Long> lapTimes;

    public LapAdapter(Context context, ArrayList<Long> lapTimes) {
        this.context = context;
        this.lapTimes = lapTimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lap_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        long lapTime = lapTimes.get(position);
        int minutes = (int) (lapTime / 60000);
        int seconds = (int) (lapTime % 60000 / 1000);
        int milliseconds = (int) (lapTime % 1000);

        @SuppressLint("DefaultLocale") String lapTimeString = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);

        // Set lap number (position + 1 because position is zero-based)
        holder.lapNumberTextView.setText((position + 1) + ".  ");
        holder.lapTimeTextView.setText(lapTimeString);
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
}
