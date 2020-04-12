package com.example.pointme.adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

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
    private NestedScrollView scrollView;
    private int minHeight;
    private static int currentPosition = 0;
    private Context context;

    public ProfileAdapter(List<Event> eventsList, List<Appointment> appointmentsList, ProfileAdapterCallback callback, Context context, NestedScrollView scrollView) {
        this.eventsList = eventsList;
        this.appointmentsList = appointmentsList;
        mAdapterCallback = callback;
        this.context = context;
        this.scrollView = scrollView;
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

        cardView.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded) {
                    Log.i("PROFILE ADAPTER", "Exapnded");
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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