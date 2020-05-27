package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.pointme.R;

public class ProvidersItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public TextView name;
    public ImageView profileImage;
    public ToggleButton favoritesButton;
    private final ProvidersAdapter.ProvidersAdapterOnClickHandler mClickHandler;
    private final ProvidersAdapter.FavoriteOnClickHandler mFavoriteClickHandler;

    ProvidersItemHolder(View itemView, ProvidersAdapter.ProvidersAdapterOnClickHandler mClickHandler, ProvidersAdapter.FavoriteOnClickHandler mFavoriteClickHandler) {
        super(itemView);
        name = itemView.findViewById(R.id.txtName);
        profileImage = itemView.findViewById(R.id.profile_image);
        favoritesButton = itemView.findViewById(R.id.button_favorite);
        this.mClickHandler = mClickHandler;
        this.mFavoriteClickHandler = mFavoriteClickHandler;
        itemView.setOnClickListener(this);
        favoritesButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickHandler.onClick(getAdapterPosition());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        mFavoriteClickHandler.onFavoriteClick(compoundButton, isChecked, getAdapterPosition());
    }
}