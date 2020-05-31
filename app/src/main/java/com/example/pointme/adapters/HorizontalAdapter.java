package com.example.pointme.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalItemHolder> {

    private static final String TAG = WorkshopAdapter.class.getSimpleName();
    private Context mContext;
    private TreeMap<String, String> row = new TreeMap<>();
    String key;
    String value;

    private final int VERTICAL = 1;
    private final int HORIZONTAL = 2;

//    public interface WorkshopAdapterOnClickHandler {
//        void onClick(int position);
//    }

    public HorizontalAdapter(TreeMap<String, String> row) {
        this.row.putAll(row);
    }

    @Override
    public HorizontalItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.inner_workshop_view, parent, false);

        return new HorizontalItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalItemHolder holder, int position) {
        key = (String) row.keySet().toArray()[position];
        value = row.get(key);
        fixDateTimeView();
        holder.dateTextview.setText(key);
        holder.timeTextview.setText(value);
    }

    private void fixDateTimeView() {

        String year = key.substring(0, 4);
        String month = key.substring(4, 6);
        String day = key.substring(6);
        key = day + "/" + month + "/" + year;

        String stHour = value.substring(0, 2);
        String stMin = value.substring(2, 4);
        String endHour = value.substring(4, 6);
        String endMin = value.substring(6, 8);
        value = stHour + ":" + stMin + " - " + endHour + ":" + endMin;
    }

    @Override
    public int getItemCount() {
        return row.size();
    }
}