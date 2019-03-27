package com.example.pointme.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.Interfaces.ProfileAdapterCallback;
import com.example.pointme.R;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProvidersInfo;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileEventItemHolder> {

    private List<Event> itemList;
    private ProfileAdapterCallback mAdapterCallback;

    private int minHeight;

    public ProfileAdapter(List<Event> itemList, ProfileAdapterCallback callback) {
        this.itemList = itemList;
        mAdapterCallback = callback;
    }

    public void newList(List<Event> itemList) {
        if (this.itemList != null) {
            this.itemList.clear();
        }
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (itemList != null) {
            ret = itemList.size();
        }
        return ret;
    }

    @Override
    public void onBindViewHolder(ProfileEventItemHolder contactViewHolder, int i) {
        Event info = itemList.get(i);
        contactViewHolder.geNameText().setText(info.getName());
        contactViewHolder.getDescriptionText().setText(info.getDesc());
    }

    @Override
    public ProfileEventItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.profile_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.title);
        final CardView cardview = itemView.findViewById(R.id.card_view);

        cardview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                cardview.getViewTreeObserver().removeOnPreDrawListener(this);
                minHeight = cardview.getHeight();
                Log.d("Adapter", minHeight + "");
                ViewGroup.LayoutParams layoutParams = cardview.getLayoutParams();
                layoutParams.height = minHeight;
                cardview.setLayoutParams(layoutParams);

                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adapter", "test" + v.getHeight());
                mAdapterCallback.onMethodCallback(cardview, minHeight, v.getHeight() + 100);
            }
        });
        return new ProfileEventItemHolder(itemView);
    }
}