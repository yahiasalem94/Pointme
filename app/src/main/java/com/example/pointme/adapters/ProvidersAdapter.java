package com.example.pointme.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.pointme.R;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.GlideApp;
import com.example.pointme.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersItemHolder> {

    private List<ServiceProvider> itemList;
    private SharedPreference sharedPreference;
    private Context mContext;
    private ProvidersAdapterOnClickHandler mClickHandler;
    private FavoriteOnClickHandler mFavoriteClickHandler;

    public interface ProvidersAdapterOnClickHandler {
        void onClick(int position);
    }

    public interface FavoriteOnClickHandler {
        void onFavoriteClick(CompoundButton compoundButton, boolean isChecked, int position);
    }

    public ProvidersAdapter(Context mContext, SharedPreference sharedPreference, ProvidersAdapterOnClickHandler mClickHandler, FavoriteOnClickHandler mFavoriteClickHandler ) {
        this.mClickHandler = mClickHandler;
        this.mFavoriteClickHandler = mFavoriteClickHandler;
        this.mContext = mContext;
        this.sharedPreference = sharedPreference;
    }

    public void setProvidersData(ArrayList<ServiceProvider> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public ProvidersItemHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // Inflate the RecyclerView item layout xml.
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_of_providers_card_layout, viewGroup, false);

        return new ProvidersItemHolder(itemView, mClickHandler, mFavoriteClickHandler);
    }

    @Override
    public void onBindViewHolder(ProvidersItemHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());

        GlideApp.with(mContext)
                .load(itemList.get(position).getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .fitCenter())
                .into(holder.profileImage);

        if (checkFavoriteItem(position)) {
            holder.favoritesButton.setChecked(true);
        }
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(int position) {
        List<ServiceProvider> favorites = sharedPreference.getFavorites(mContext);
        if (favorites != null) {
            for (ServiceProvider info : favorites) {
                if (info.equals(itemList.get(position))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (null == itemList) return 0;
        return itemList.size();
    }
}