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

    private List<ServiceProvider> profileInfosList;
    private SharedPreference sharedPreference;
    private Context context;
    private ProvidersAdapterOnClickHandler mClickHandler;

    public interface ProvidersAdapterOnClickHandler {
        void onClick(int position);
    }

    public ProvidersAdapter(Context context, ProvidersAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
        this.context = context;
        sharedPreference = new SharedPreference();
    }

    public void setProvidersData(ArrayList<ServiceProvider> profileInfosList) {
        this.profileInfosList = profileInfosList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(ProvidersItemHolder holder, int i) {
        final ServiceProvider info = profileInfosList.get(i);
        holder.name.setText(info.getName());

        if (checkFavoriteItem(info)) {
            holder.favoritesButton.setChecked(true);
        }

        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        holder.favoritesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
    public boolean checkFavoriteItem(ServiceProvider checkInfo) {
        boolean check = false;
        List<ServiceProvider> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (ServiceProvider info : favorites) {
                if (info.equals(checkInfo)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public ProvidersItemHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_of_providers_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.txtName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mRecyclerViewListener.onClickPI(profileInfosList.get(i));
            }
        });
        return new ProvidersItemHolder(itemView, mClickHandler);
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (profileInfosList != null) {
            ret = profileInfosList.size();
        }
        return ret;
    }
}