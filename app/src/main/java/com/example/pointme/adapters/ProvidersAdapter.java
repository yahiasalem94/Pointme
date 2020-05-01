package com.example.pointme.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersItemHolder> {

    private List<ServiceProvider> serviceProviderList;
    private SharedPreference sharedPreference;
    private Context context;
    private ProvidersAdapterOnClickHandler mClickHandler;
    private FavoriteOnClickHandler mFavoriteClickHandler;

    public interface ProvidersAdapterOnClickHandler {
        void onClick(int position);
    }

    public interface FavoriteOnClickHandler {
        void onFavoriteClick(CompoundButton compoundButton, boolean isChecked, int position);
    }

    public ProvidersAdapter(Context context, SharedPreference sharedPreference, ProvidersAdapterOnClickHandler mClickHandler, FavoriteOnClickHandler mFavoriteClickHandler ) {
        this.mClickHandler = mClickHandler;
        this.mFavoriteClickHandler = mFavoriteClickHandler;
        this.context = context;
        this.sharedPreference = sharedPreference;
    }

    public void setProvidersData(ArrayList<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
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
        holder.name.setText(serviceProviderList.get(position).getName());

        if (checkFavoriteItem(position)) {
            holder.favoritesButton.setChecked(true);
        }
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(int position) {
        List<ServiceProvider> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (ServiceProvider info : favorites) {
                if (info.equals(serviceProviderList.get(position))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (null == serviceProviderList) return 0;
        return serviceProviderList.size();
    }
}