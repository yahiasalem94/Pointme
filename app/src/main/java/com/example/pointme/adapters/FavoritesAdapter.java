package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.models.ProvidersInfo;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesEventItemHolder> {

    private List<ProfileInfo> itemList;
    private AdapterCallback mAdapterCallback;

    public FavoritesAdapter(List<ProfileInfo> itemList, AdapterCallback callback) {
        this.itemList = itemList;
        mAdapterCallback = callback;
    }

    public void newList(List<ProfileInfo> itemList) {
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
        ProfileInfo info = itemList.get(i);
        contactViewHolder.geNameText().setText(info.getName());
        contactViewHolder.getTitleText().setText(info.getTitle());
    }

    @Override
    public FavoritesEventItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.favorites_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onMethodCallback(titleView.getText().toString());
            }
        });
        return new FavoritesEventItemHolder(itemView);
    }
}