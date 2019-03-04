package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class ProfileEventItemHolder extends RecyclerView.ViewHolder {

    private TextView description;
    private TextView name;

    public ProfileEventItemHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
        }
    }

    public TextView geNameText() {
        return name;
    }

    public TextView getDescriptionText() {
        return description;
    }
}