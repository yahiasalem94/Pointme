package com.example.pointme.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pointme.R;

public class ProfileEventItemHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView name, eventTitle, description;
    private Button book;
    private LinearLayout linearLayout;
    private CardView cardView;

    public ProfileEventItemHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            name = itemView.findViewById(R.id.txtName);
            imageView = itemView.findViewById(R.id.card_view_image);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.description);
            book = itemView.findViewById(R.id.bookNow);
        }
    }

    public TextView geNameText() {
        return name;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getEventTitle() {
        return eventTitle;
    }

    public TextView getDescription() {
        return description;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public CardView getCardView() {
        return cardView;
    }

    public Button getBook() {
        return book;
    }
}