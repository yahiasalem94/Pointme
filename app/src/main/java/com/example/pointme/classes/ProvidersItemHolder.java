package com.example.pointme.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pointme.R;

public class ProvidersItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView surname;
    private TextView email;
    private TextView title;

    public ProvidersItemHolder(View itemView) {
        super(itemView);

        if (itemView != null)
        {
            name = itemView.findViewById(R.id.txtName);
            surname = itemView.findViewById(R.id.txtSurname);
            email = itemView.findViewById(R.id.txtEmail);
            title = itemView.findViewById(R.id.title);
        }
    }
    public TextView geNameText() {
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
}