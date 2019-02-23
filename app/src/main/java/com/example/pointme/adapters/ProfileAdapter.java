package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileEventItemHolder> {

    private List<ProfileEventInfo> itemList;
    private AdapterCallback mAdapterCallback;

    public ProfileAdapter(List<ProfileEventInfo> itemList , AdapterCallback callback) {
        this.itemList = itemList;
        mAdapterCallback = callback;
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(ProfileEventItemHolder contactViewHolder, int i) {
        ProfileEventInfo info = itemList.get(i);
        contactViewHolder.geNameText().setText(info.getName());
        contactViewHolder.getTitleText().setText(info.getTitle());
    }

    @Override
    public ProfileEventItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.profile_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onMethodCallback(titleView.getText().toString());
            }
        });
        return new ProfileEventItemHolder(itemView);
    }
}