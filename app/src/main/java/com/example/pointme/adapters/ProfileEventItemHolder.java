package com.example.pointme.adapters;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.pointme.R;
import com.example.pointme.constants.Type;

public class ProfileEventItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView name, title, description;
    public Button book;
    public @Type int type;
    public LinearLayout linearLayout;
    public ExpandableCardView cardView;
    private final ProfileAdapter.ProfileAdapterOnClickHandler mClickHandler;

    ProfileEventItemHolder(View itemView, ProfileAdapter.ProfileAdapterOnClickHandler mClickHandler, Context context) {
        super(itemView);

        name = itemView.findViewById(R.id.txtName);
        imageView = itemView.findViewById(R.id.card_view_image);
        title = itemView.findViewById(R.id.eventTitle);
        description = itemView.findViewById(R.id.description);
        cardView = itemView.findViewById(R.id.profile);
        cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        book = itemView.findViewById(R.id.bookNow);
        this.mClickHandler = mClickHandler;
        book.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mClickHandler.onClick(type, getAdapterPosition());
    }
}