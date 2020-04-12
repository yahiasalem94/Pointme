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

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.utils.SharedPreference;

import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersItemHolder> {

    private List<ProfileInfo> profileInfosList;
    private RecyclerViewClickListener mRecyclerViewListener;
    private SharedPreference sharedPreference;
    private Context context;

    public ProvidersAdapter(List<ProfileInfo> profileInfosList, RecyclerViewClickListener mRecyclerViewListener, Context context) {
        this.profileInfosList = profileInfosList;
        this.mRecyclerViewListener = mRecyclerViewListener;
        this.context = context;
        sharedPreference = new SharedPreference();
    }

    public void newList(List<ProfileInfo> profileInfosList) {
        if (this.profileInfosList != null) {
            this.profileInfosList.clear();
        }
        this.profileInfosList = profileInfosList;
    }

    @Override
    public void onBindViewHolder(ProvidersItemHolder contactViewHolder, int i) {
        final ProfileInfo info = profileInfosList.get(i);
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
    public boolean checkFavoriteItem(ProfileInfo checkInfo) {
        boolean check = false;
        List<ProfileInfo> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (ProfileInfo info : favorites) {
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
                mRecyclerViewListener.onClickPI(profileInfosList.get(i));
            }
        });
        return new ProvidersItemHolder(itemView);
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