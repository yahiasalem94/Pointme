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
import com.example.pointme.R;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.Meeting;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileEventItemHolder> {

    private List<Meeting> meetings;

    private ExpandableCardView cardView;
    private NestedScrollView scrollView;
    private int minHeight;
    private static int currentPosition = 0;
    private Context context;

    private ProfileAdapterOnClickHandler mClickHandler;

    public interface ProfileAdapterOnClickHandler {
        void onClick(@Type int type, int position);
    }


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

        return new ProfileEventItemHolder(itemView, mClickHandler, context);
    }

    @Override
    public void onBindViewHolder(final ProfileEventItemHolder holder, final int position) {
            holder.cardView.setTitle(meetings.get(position).getName());
            holder.cardView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.title.setText(meetings.get(position).getDesc());
            holder.description.setText(meetings.get(position).getDesc());
            if (meetings.get(position) instanceof Appointment) {
                holder.type = Type.APPOINTMENT;
            } else if (meetings.get(position) instanceof Event) {
                holder.type = Type.EVENT;
            }

        holder.cardView.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded) {
                    Log.i("PROFILE ADAPTER", "Exapnded");
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }
        });
    }



}