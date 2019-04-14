package com.example.pointme.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.pointme.constants.Type;
import com.example.pointme.interfaces.ProfileAdapterCallback;
import com.example.pointme.R;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileEventItemHolder> {

    private List<Event> eventsList;
    private List<Appointment> appointmentsList;
    private ProfileAdapterCallback mAdapterCallback;

    private ExpandableCardView cardView;
    private int minHeight;
    private static int currentPosition = 0;
    private Context context;

    public ProfileAdapter(List<Event> eventsList, List<Appointment> appointmentsList, ProfileAdapterCallback callback, Context context) {
        this.eventsList = eventsList;
        this.appointmentsList = appointmentsList;
        mAdapterCallback = callback;
        this.context = context;
    }

    public void newList(List<Event> eventsList, List<Appointment> appointmentsList) {
        if (this.eventsList != null) {
            this.eventsList.clear();
        }
        if (this.appointmentsList != null){
            this.appointmentsList.clear();
        }
        this.eventsList = eventsList;
        this.appointmentsList = appointmentsList;
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (eventsList != null) {
            ret += eventsList.size();
        }
        if (appointmentsList != null){
            ret += appointmentsList.size();
        }
        return ret;
    }

    @Override
    public void onBindViewHolder(final ProfileEventItemHolder contactViewHolder, final int position) {
        if (position < eventsList.size()){
            Event info = eventsList.get(position);
            cardView.setTitle(info.getName());
            cardView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            contactViewHolder.getTitle().setText(info.getDesc());
            contactViewHolder.getDescription().setText(info.getDesc());
            contactViewHolder.setType(Type.EVENT);
        } else {
            Appointment info = appointmentsList.get(position - eventsList.size());
            cardView.setTitle(info.getName());
            cardView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            contactViewHolder.getTitle().setText(info.getDesc());
            contactViewHolder.getDescription().setText(info.getDesc());
            contactViewHolder.setType(Type.APPOINTMENT);
        }
//        contactViewHolder.geNameText().setText(info.getName());
//        contactViewHolder.getImageView().setImageResource(R.drawable.back_2);
        contactViewHolder.getBook().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contactViewHolder.getType() == Type.EVENT) {
                    Event event = eventsList.get(position);
                    mAdapterCallback.onBookPressed(event, Type.EVENT);
                }else if (contactViewHolder.getType() == Type.APPOINTMENT){
                    Appointment appointment = appointmentsList.get(position - eventsList.size());
                    mAdapterCallback.onBookPressed(appointment, Type.APPOINTMENT);
                }
            }
        });
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
        cardView.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded) {
                    Log.i("PROFILE ADAPTER", "Exapnded");
                }
            }
        });
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