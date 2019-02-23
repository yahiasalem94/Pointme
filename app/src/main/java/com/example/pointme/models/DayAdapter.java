package com.example.pointme.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pointme.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    private ArrayList<String> list;
    private ArrayList<TextView> tvs;
    private HashMap<DayHolder, ArrayList<TextView>> holders;
    private Context context;
    private Animation mSlideUp;
    private Animation mSlideDown;

    private OnDayClicked onClick;

    //make interface like this
    public interface OnDayClicked {
        void onDayClick(DayHolder viewHolder, int position);
    }

    public DayAdapter(ArrayList<String> list, Context context){
        this.list = list;
        this.context = context;
        tvs = new ArrayList<>();
        holders = new HashMap<>();
        mSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_text);
        mSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_text);
    }

    @NonNull
    @Override
    public DayAdapter.DayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_picker_list, viewGroup, false);
        //holders.add(new DayHolder(view));
        return new DayHolder(view);//holders.get(i);
    }

    @Override
    public void onBindViewHolder(@NonNull final DayAdapter.DayHolder holder, final int i) {
        if(!holders.containsKey(holder)){
            holders.put(holder,null);
        }
        holder.mDayTextView.setText(list.get(i));
        if (holder.isFlag()){
            /*holder.mLayout.clearAnimation();
            holder.mLayout.startAnimation(mSlideDown);*/
            holder.mLayout.setVisibility(View.VISIBLE);
        }else{
            /*holder.mLayout.clearAnimation();
            holder.mLayout.startAnimation(mSlideUp);*/
            holder.mLayout.setVisibility(View.GONE);
        }

        if(holders.get(holder)!=null) {
            ArrayList<TextView> tvlist = holders.get(holder);
            for (TextView textView : tvlist) {
                if (textView.getParent() != null) {
                    ((ViewGroup) textView.getParent()).removeView(textView);
                }
                holder.mLayout.addView(textView);
            }
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.mTimeButton.getLayoutParams();
            lp.addRule(RelativeLayout.BELOW, tvlist.get(tvlist.size()-1).getId());
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            holder.mTimeButton.setLayoutParams(lp);
        }
        holder.mDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onDayClick(holder, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClick(OnDayClicked onClick)
    {
        this.onClick=onClick;
    }

    public void setTVVisible(String text, DayHolder holder){
        int id;
        if(holders.get(holder) == null){
            tvs = new ArrayList<>();
            id = R.id.day_text_view;
        }else{
            tvs = holders.get(holder);
            id = tvs.get(tvs.size()-1).getId();
        }
        TextView tv = new TextView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        lp.addRule(RelativeLayout.BELOW, id);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tv.setLayoutParams(lp);
        tv.setId(View.generateViewId());
        tv.setText(text);
        tvs.add(tv);
        holders.put(holder, tvs);
    }

    public class DayHolder extends RecyclerView.ViewHolder{
        public TextView mDayTextView;
        public ImageView mCheckImageView;
        public Button mTimeButton;
        public RelativeLayout mLayout;
        private boolean flag;


        public DayHolder(View itemView) {
            super(itemView);
            mDayTextView = (TextView) itemView.findViewById(R.id.day_text_view);
            mTimeButton = (Button) itemView.findViewById(R.id.btn_addtime);
            mCheckImageView = (ImageView) itemView.findViewById(R.id.check_image_view);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.rl_daypickeritem);
            flag = false;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
