package com.example.pointme.adapters;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pointme.R;
import com.example.pointme.constants.Type;

public class ProfileEventItemHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView name, title, description;
    private Button book;
    private @Type int type;
    private LinearLayout linearLayout;
    private CardView cardView;

    public ProfileEventItemHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            name = itemView.findViewById(R.id.txtName);
            imageView = itemView.findViewById(R.id.card_view_image);
            title = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.description);
            book = itemView.findViewById(R.id.bookNow);
        }
    }

    public TextView getNameText() {
        return name;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTitle() {
        return title;
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

    public @Type int getType() {
        return type;
    }

    public void setType(@Type int type) {
        this.type = type;
    }
}