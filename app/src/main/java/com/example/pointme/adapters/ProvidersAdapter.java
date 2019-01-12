package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.R;
import com.example.pointme.classes.ProvidersInfo;
import com.example.pointme.classes.ProvidersItemHolder;

import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersItemHolder> {

    private List<ProvidersInfo> providersInfoList;

    public ProvidersAdapter(List<ProvidersInfo> providersInfoList) {
        this.providersInfoList = providersInfoList;
    }

    @Override
    public int getItemCount() {
        return providersInfoList.size();
    }

    @Override
    public void onBindViewHolder(ProvidersItemHolder contactViewHolder, int i) {
        ProvidersInfo info = providersInfoList.get(i);
        contactViewHolder.geNameText().setText(info.getName());
        contactViewHolder.getSurnameText().setText(info.getSurname());
        contactViewHolder.getEmailText().setText(info.getEmail());
        contactViewHolder.getTitleText().setText(info.getName() + " " + info.getSurname());
    }

    @Override
    public ProvidersItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_of__providers_card_layout, viewGroup, false);

        return new ProvidersItemHolder(itemView);
    }
}