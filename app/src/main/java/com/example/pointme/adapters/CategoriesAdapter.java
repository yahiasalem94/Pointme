package com.example.pointme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;
import com.example.pointme.activities.ListOfServiceProviders;
import com.example.pointme.classes.CategoriesItem;
import com.example.pointme.classes.CategoriesItemHolder;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesItemHolder> {

    private List<CategoriesItem> itemList;
    private Context context;
    public CategoriesAdapter(List<CategoriesItem> itemList, Context c) {
        this.itemList = itemList;
        this.context = c;
    }

    @Override
    public CategoriesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.categories_card_layout, parent, false);

        // Get car title text view object.
        final TextView titleView = itemView.findViewById(R.id.card_view_image_title);
        // Get car image view object.
        final ImageView imageView = itemView.findViewById(R.id.card_view_image);
        // When click the image.

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get car title text.
//                String title = titleView.getText().toString();
//                // Create a snackbar and show it.
//                Snackbar snackbar = Snackbar.make(imageView, "You click " + title +" image", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                Intent intent = new Intent(v.getContext(), ListOfServiceProviders.class);
//                v.getContext().startActivity(intent);
//            }
//        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get car title text.
                String title = titleView.getText().toString();

                Intent intent = new Intent(v.getContext(), ListOfServiceProviders.class);
                intent.putExtra("title", title);
                v.getContext().startActivity(intent);
            }
        });

                // Create and return our custom Car Recycler View Item Holder object.
                CategoriesItemHolder ret = new CategoriesItemHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(CategoriesItemHolder holder, int position) {
        if(itemList!=null) {
            // Get car item dto in list.
            CategoriesItem carItem = itemList.get(position);

            if(carItem != null) {
                // Set car item title.
                holder.getCategoryTitleText().setText(carItem.getName());
                // Set car image resource id.
                holder.getImageView().setImageResource(carItem.getImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(itemList!=null)
        {
            ret = itemList.size();
        }
        return ret;
    }
}