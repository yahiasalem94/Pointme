package com.example.pointme.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.pointme.interfaces.ProfileAdapterCallback;
import com.example.pointme.R;
import com.example.pointme.models.Event;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileEventItemHolder> {

    private List<Event> itemList;
    private ProfileAdapterCallback mAdapterCallback;

    private ExpandableCardView cardView;
    private int minHeight;
    private static int currentPosition = 0;
    private Context context;

    public ProfileAdapter(List<Event> itemList, ProfileAdapterCallback callback, Context context) {
        this.itemList = itemList;
        mAdapterCallback = callback;
        this.context = context;
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
    public void onBindViewHolder(ProfileEventItemHolder contactViewHolder, final int position) {
        Event info = itemList.get(position);
//        contactViewHolder.geNameText().setText(info.getName());
//        contactViewHolder.getImageView().setImageResource(R.drawable.back_2);
        cardView.setTitle(info.getName());
        cardView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        contactViewHolder.getEventTitle().setText(info.getDesc());
        contactViewHolder.getDescription().setText(info.getDesc());

        //  contactViewHolder.getLinearLayout().setVisibility(View.GONE);

        //if the position is equals to the item position which is to be expanded
      /*  if (currentPosition == position) {
            //creating an animation
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.card_anim);

            //toggling visibility
            contactViewHolder.getLinearLayout().setVisibility(View.VISIBLE);

            //adding sliding effect
            contactViewHolder.getLinearLayout().startAnimation(anim);
        }

        contactViewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public ProfileEventItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.profile_card_layout, viewGroup, false);
        cardView = itemView.findViewById(R.id.profile);
        cardView.findViewById(R.id.card).setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));

        return new ProfileEventItemHolder(itemView);
    }
}