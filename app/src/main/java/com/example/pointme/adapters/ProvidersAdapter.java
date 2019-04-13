package com.example.pointme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.ProvidersInfo;
import com.example.pointme.utils.SharedPreference;

import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersItemHolder> {

    private List<ProvidersInfo> providersInfoList;
    private RecyclerViewClickListener mRecyclerViewListener;
    private SharedPreference sharedPreference;
    private Context context;

    public ProvidersAdapter(List<ProvidersInfo> providersInfoList, RecyclerViewClickListener mRecyclerViewListener, Context context) {
        this.providersInfoList = providersInfoList;
        this.mRecyclerViewListener = mRecyclerViewListener;
        this.context = context;
        sharedPreference = new SharedPreference();
    }

    public void newList(List<ProvidersInfo> providersInfoList) {
        if (this.providersInfoList != null) {
            this.providersInfoList.clear();
        }
        this.providersInfoList = providersInfoList;
    }

    @Override
    public void onBindViewHolder(ProvidersItemHolder contactViewHolder, int i) {
        final ProvidersInfo info = providersInfoList.get(i);
        contactViewHolder.getNameText().setText(info.getName());

        if (checkFavoriteItem(info)) {
            contactViewHolder.getFavoritesButton().setChecked(true);
        }

        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        contactViewHolder.getFavoritesButton().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //animation
                compoundButton.startAnimation(scaleAnimation);

                if (isChecked) {
                    sharedPreference.addFavorite(context, info);
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_LONG).show();
                } else {
                    sharedPreference.removeFavorite(context, info);

                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(ProvidersInfo checkInfo) {
        boolean check = false;
        List<ProvidersInfo> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (ProvidersInfo info : favorites) {
                if (info.equals(checkInfo)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public ProvidersItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_of_providers_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.txtName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewListener.onClick(titleView.getText().toString());
            }
        });
        return new ProvidersItemHolder(itemView);
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (providersInfoList != null) {
            ret = providersInfoList.size();
        }
        return ret;
    }
}