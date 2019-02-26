package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesItemHolder> {

    private List<CategoriesItem> itemList;
    private AdapterCallback mAdapterCallback;

    public CategoriesAdapter(List<CategoriesItem> itemList, AdapterCallback callback) {
        this.itemList = itemList;
        this.mAdapterCallback = callback;
    }

    public void newList(List<CategoriesItem> itemList) {
        if (this.itemList != null) {
            this.itemList.clear();
        }
        this.itemList = itemList;
    }
    @Override
    public CategoriesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.categories_card_layout, parent, false);

        // Get car title text view object.
        final TextView titleView = itemView.findViewById(R.id.card_view_image_title);

        // When click the image.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get car title text.
               /* String title = titleView.getText().toString();

                Intent intent = new Intent(v.getContext(), ListOfServiceProvidersFragment.class);
                intent.putExtra("title", title);
                v.getContext().startActivity(intent);*/
               mAdapterCallback.onMethodCallback(titleView.getText().toString());
            }
        });

        // Create and return our custom item Recycler View Item Holder object.
        return new CategoriesItemHolder(itemView);
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