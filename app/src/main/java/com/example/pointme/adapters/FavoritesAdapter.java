package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.Interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.models.ProvidersInfo;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesEventItemHolder> {

    private List<ProvidersInfo> itemList;
    private RecyclerViewClickListener mRecyclerViewListener;

    public FavoritesAdapter(List<ProvidersInfo> itemList, RecyclerViewClickListener callback) {
        this.itemList = itemList;
        mRecyclerViewListener = callback;
    }

    public void newList(List<ProvidersInfo> itemList) {
        if (this.itemList != null) {
            this.itemList.clear();
        }
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(FavoritesEventItemHolder contactViewHolder, int i) {
        ProvidersInfo info = itemList.get(i);
        contactViewHolder.getNameText().setText(info.getName());
    }

    @Override
    public FavoritesEventItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.favorites_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.txtName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewListener.onClick(titleView.getText().toString());
            }
        });
        return new FavoritesEventItemHolder(itemView);
    }
}