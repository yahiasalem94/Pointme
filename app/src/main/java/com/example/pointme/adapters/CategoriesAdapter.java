package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.CategoriesItem;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesItemHolder> {

    private List<CategoriesItem> itemList;
    private RecyclerViewClickListener mRecyclerViewListener;

    public CategoriesAdapter(List<CategoriesItem> itemList, RecyclerViewClickListener recyclerViewListener) {
        this.itemList = itemList;
        this.mRecyclerViewListener = recyclerViewListener;
    }

    public void newList(List<CategoriesItem> itemList) {
        if (this.itemList != null) {
            this.itemList.clear();
        }

        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public CategoriesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.categories_card_layout, parent, false);

        // Create and return our custom item Recycler View Item Holder object.
        return new CategoriesItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoriesItemHolder holder, final int position) {
        if(itemList!=null) {
            // Get item in list.
            CategoriesItem item = itemList.get(position);

            if (item != null) {
                // Set image resource id.
                holder.getImageView().setImageResource(item.getImageId());
            }
        }
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewListener.onClick(itemList.get(position).getName());
            }
        });
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