package com.example.pointme.adapters;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.CategoriesModel;
import com.example.pointme.utils.GlideApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesItemHolder> {

    private static final String TAG = CategoriesAdapter.class.getSimpleName();
    private ArrayList<String> itemList;
    private Context mContext;
    private CategoriesAdapterOnClickHandler mClickHandler;

    public interface CategoriesAdapterOnClickHandler {
        void onClick(int position);
    }

    public CategoriesAdapter(Context mContext, CategoriesAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
        this.mContext = mContext;
    }

    public void setCategoriesData(ArrayList<String> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public CategoriesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.categories_card_layout, parent, false);

        // Create and return our custom item Recycler View Item Holder object.
        return new CategoriesItemHolder(itemView, mClickHandler);
    }

    @Override
    public void onBindViewHolder(CategoriesItemHolder holder, final int position) {
        GlideApp.with(mContext)
                .load(itemList.get(position))
                .apply(new RequestOptions()
                        .placeholder(new ColorDrawable(Color.BLACK))
                        .fitCenter()
                        .dontAnimate())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onLoadFailed " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourveReady" );
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (null == itemList) return 0;
        return itemList.size();
    }
}