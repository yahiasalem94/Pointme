package com.example.pointme.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

import java.util.ArrayList;

public class TimeAdapter extends Adapter<TimeAdapter.Holder> {

    private ArrayList<String> list;
    public static ArrayList<Holder> holders;
    private Context context;
    public static boolean isSelected;
    public static int selPos;

    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(Holder viewHolder, int position);
    }

    public TimeAdapter(ArrayList<String> list, Context context){
        holders = new ArrayList<>();
        isSelected = false;
        selPos = 0;
        this.list = list;
        this.context = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_picker_list, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder viewHolder, final int i) {
        viewHolder.mTimeTextView.setText(list.get(i));
        viewHolder.mTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(viewHolder, i);
            }
        });
        if (isSelected){
            if (!holders.get(selPos).equals(viewHolder)){
                viewHolder.mTimeTextView.setTextColor(Color.GRAY);
            }
        }
        holders.add(viewHolder);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView mTimeTextView;
        public ImageView mCheckImageView;


        public Holder(View itemView) {
            super(itemView);
            mTimeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
            mCheckImageView = (ImageView) itemView.findViewById(R.id.check_image_view);

        }
    }

}
