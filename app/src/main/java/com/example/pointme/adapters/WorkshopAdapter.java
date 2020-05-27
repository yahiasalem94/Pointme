package com.example.pointme.adapters;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopItemHolder> {

    private static final String TAG = WorkshopAdapter.class.getSimpleName();
    private List<TreeMap<String, String>> itemList = new ArrayList<>();
    private Context mContext;
    private WorkshopAdapterOnClickHandler mClickHandler;

    private HorizontalAdapter mAdapter;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private final int VERTICAL = 1;
    private final int HORIZONTAL = 2;

    public interface WorkshopAdapterOnClickHandler {
        void onClick(int position);
    }

    public WorkshopAdapter(Context mContext, WorkshopAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
        this.mContext = mContext;

    }

    public void setWorkshopData(List<TreeMap<String, String>> workshopDates) {
        if (this.itemList != null)
            this.itemList.clear();

        this.itemList.addAll(workshopDates);
        notifyDataSetChanged();
    }

    @Override
    public WorkshopItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.workshop_card_layout, parent, false);

        // Create and return our custom item Recycler View Item Holder object.
        return new WorkshopItemHolder(itemView, mClickHandler);
    }

    @Override
    public void onBindViewHolder(WorkshopItemHolder holder, final int position) {
//        holder.dateTextview.setText(itemList.get(position).entrySet().iterator().next().getKey());
//        holder.timeTextview.setText(itemList.get(position).entrySet().iterator().next().getValue());

        LinearLayoutManager childLayoutManager = new LinearLayoutManager(mContext,  RecyclerView.HORIZONTAL, false);

        mAdapter = new HorizontalAdapter(itemList.get(position));
        childLayoutManager.setInitialPrefetchItemCount(2);
        holder.recyclerView.setLayoutManager(childLayoutManager);
        holder.recyclerView.setAdapter(mAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}