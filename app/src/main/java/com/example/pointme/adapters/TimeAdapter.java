package com.example.pointme.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.R;

import java.util.ArrayList;

public class TimeAdapter extends  RecyclerView.Adapter<TimeAdapterItemHolder> {

    private ArrayList<String> data = new ArrayList<>();
    private Context context;
    public static boolean isSelected;
    public static int selPos;
    private TimeAdapterOnClickHandler mClickHandler;

//    //declare interface
//    private OnItemClicked onClick;

//    //make interface like this
//    public interface OnItemClicked {
//        void onItemClick(Holder viewHolder, int position);
//    }

    public interface TimeAdapterOnClickHandler {
        void onClick(int position);
    }

    public TimeAdapter(Context context, TimeAdapterOnClickHandler mClickHandler){
//        holders = new ArrayList<>();
//        isSelected = false;
        selPos = 0;
//        this.list = list;
        this.context = context;
        this.mClickHandler = mClickHandler;
//        setHasStableIds(true);
    }

    public void setTimesData(ArrayList<String> itemList) {
        if (this.data !=  null)
            this.data.clear();
        this.data.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeAdapterItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_picker_list, viewGroup, false);

        return new TimeAdapterItemHolder(view, mClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeAdapterItemHolder holder, int i) {
        String stHour = data.get(i).substring(0, 2);
        String stMin = data.get(i).substring(2, 4);
        String endHour = data.get(i).substring(4, 6);
        String endMin = data.get(i).substring(6, 8);
        String time = stHour + ":" + stMin + " - " + endHour + ":" + endMin;
        holder.mTimeTextView.setText(time);
    }


    @Override
    public int getItemCount() {
        if (null == data) return 0;
        return data.size();
    }


}
