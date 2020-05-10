package com.example.pointme.adapters;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.pointme.viewModels.ProvidersViewModel;

public class ProfileEventItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView eventTvTitle;
    public ConstraintLayout expandableView;
    public TextView description;
    private TextView feesTv;
    public Button book;
    public @Type int type;
    public LinearLayout linearLayout;
    public CardView cardView;
    private final ProfileAdapter.ProfileAdapterOnClickHandler mClickHandler;
//    private final ProfileAdapter.ProfileAdapterOnExpandableCard mClickExpandableHandler;

    ProfileEventItemHolder(View itemView, ProfileAdapter.ProfileAdapterOnClickHandler mClickHandler/*, ProfileAdapter.ProfileAdapterOnExpandableCard mClickExpandableHandler*/) {
        super(itemView);

        eventTvTitle = itemView.findViewById(R.id.eventTv_title);
        expandableView = itemView.findViewById(R.id.expandableView);
        description = itemView.findViewById(R.id.description);
        feesTv = itemView.findViewById(R.id.et_fees);
        cardView = itemView.findViewById(R.id.cardView);
        cardView.setOnClickListener(this);
//        this.mClickExpandableHandler = mClickExpandableHandler;
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
        if (view.getId() == R.id.bookNow) {
            mClickHandler.onClick(type, getAdapterPosition());
        } else if (view.getId() == R.id.expandableView) {
//            mClickExpandableHandler.onClick();
        }
    }
}