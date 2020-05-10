package com.example.pointme.adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.pointme.constants.Type;
import com.example.pointme.R;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.ExpandModel;
import com.example.pointme.models.Meeting;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileEventItemHolder>  {

    private List<Meeting> meetings;

    private NestedScrollView scrollView;
    private int minHeight;
    private static int currentPosition = 0;
    private Context context;

    private ProfileAdapterOnClickHandler mClickHandler;



    public interface ProfileAdapterOnClickHandler {
        void onClick(@Type int type, int position);
    }

//    public interface ProfileAdapterOnExpandableCard {
//        void onClick();
//    }

    public ProfileAdapter(ProfileAdapterOnClickHandler mClickHandler, Context context, NestedScrollView scrollView) {
        this.mClickHandler = mClickHandler;
        this.context = context;
        this.scrollView = scrollView;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        /* TODO Fix null pointer */
        if (meetings == null) return 0;
        return (meetings.size());
    }

    @Override
    public ProfileEventItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.profile_card_layout, viewGroup, false);

        return new ProfileEventItemHolder(itemView, mClickHandler);
    }

    @Override
    public void onBindViewHolder(final ProfileEventItemHolder holder, final int position) {
            holder.eventTvTitle.setText(meetings.get(position).getName());
            holder.description.setText(meetings.get(position).getDesc());
            if (meetings.get(position) instanceof Appointment) {
                holder.type = Type.APPOINTMENT;
            } else if (meetings.get(position) instanceof Event) {
                holder.type = Type.EVENT;
            }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.expandableView.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableView.setVisibility(View.VISIBLE);
//                    TranslateAnimation animation = new TranslateAnimation(0.0f, -200.0f, 0.0f, 0.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
//                    animation.setDuration(8500); // animation duration, change accordingly
//                    animation.setFillAfter(false);
//                    holder.eventTvTitle.startAnimation(animation);//your_view f
                } else {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableView.setVisibility(View.GONE);
                }
            }
        });
    }
}