package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.pointme.R;

public class ProvidersItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView surname;
    private TextView email;
    private TextView title;
    private ToggleButton favoritesButton;

    public ProvidersItemHolder(View itemView) {
        super(itemView);

        if (itemView != null)
        {
            name = itemView.findViewById(R.id.txtName);
//            surname = itemView.findViewById(R.id.txtSurname);
//            email = itemView.findViewById(R.id.txtEmail);
            title = itemView.findViewById(R.id.title);
            favoritesButton = itemView.findViewById(R.id.button_favorite);
        }
    }
    public TextView getNameText() {
        return name;
    }

    public TextView getSurnameText() {
        return surname;
    }

    public TextView getEmailText() {
        return email;
    }

    public TextView getTitleText() {
        return title;
    }

    public ToggleButton getFavoritesButton() {
        return favoritesButton;
    }
}