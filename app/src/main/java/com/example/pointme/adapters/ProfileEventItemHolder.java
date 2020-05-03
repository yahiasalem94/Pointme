package com.example.pointme.adapters;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.pointme.R;
import com.example.pointme.constants.Type;

public class ProfileEventItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title, description;
    public Button book;
    public @Type int type;
    public LinearLayout linearLayout;
    public ExpandableCardView cardView;
    private final ProfileAdapter.ProfileAdapterOnClickHandler mClickHandler;

    ProfileEventItemHolder(View itemView, ProfileAdapter.ProfileAdapterOnClickHandler mClickHandler, Context context) {
        super(itemView);

        title = itemView.findViewById(R.id.eventTitle);
        description = itemView.findViewById(R.id.description);
        cardView = itemView.findViewById(R.id.profile);
//        cardView.findViewById(R.id.card).setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
//        cardView.findViewById(R.id.header).setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
//        cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
//        cardView.setBackgroundColor(Color.YELLOW);
        book = itemView.findViewById(R.id.bookNow);
        this.mClickHandler = mClickHandler;
        book.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickHandler.onClick(type, getAdapterPosition());
    }
}