package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;

import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersItemHolder> {

    private List<ProvidersInfo> providersInfoList;
    private AdapterCallback mAdapterCallback;

    public ProvidersAdapter(List<ProvidersInfo> providersInfoList, AdapterCallback mAdapterCallback) {
        this.providersInfoList = providersInfoList;
        this.mAdapterCallback = mAdapterCallback;
    }

    @Override
    public int getItemCount() {
        return providersInfoList.size();
    }

    @Override
    public void onBindViewHolder(ProvidersItemHolder contactViewHolder, int i) {
        ProvidersInfo info = providersInfoList.get(i);
        contactViewHolder.getNameText().setText(info.getName());
        contactViewHolder.getSurnameText().setText(info.getSurname());
        contactViewHolder.getEmailText().setText(info.getEmail());
        contactViewHolder.getTitleText().setText(info.getName() + " " + info.getSurname());
    }

    @Override
    public ProvidersItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_of__providers_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onMethodCallback(titleView.getText().toString());
            }
        });
        return new ProvidersItemHolder(itemView);
    }
}