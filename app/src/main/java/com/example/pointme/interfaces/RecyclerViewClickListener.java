package com.example.pointme.interfaces;

import com.example.pointme.models.ServiceProvider;

public interface RecyclerViewClickListener {

    void onClick(String title);

    void onClickPI(ServiceProvider profileInfo);
}
