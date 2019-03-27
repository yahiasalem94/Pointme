package com.example.pointme.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.Interfaces.ProfileAdapterCallback;
import com.example.pointme.Interfaces.ProfileFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.backendCommunications.DBCom;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements ProfileAdapterCallback, ProfileFragmentDBInt {

    private Toolbar toolbar;
    private ProfileAdapter profileAdapter;
    private static final String ARG_PARAM1 = "param1";
    private String title;

    /*Views*/
    private TextView email;
    private TextView phoneNumber;
    private RecyclerView recyclerList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        } else {
            title = "Yahia";
        }

        profileAdapter = new ProfileAdapter(null, this);

        DBCom.getProfile(this, title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(title);

        phoneNumber = view.findViewById(R.id.phoneNumber);
        email = view.findViewById(R.id.email);

        recyclerList = view.findViewById(R.id.card_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(linearLayoutManager);
        // Set data adapter.
        recyclerList.setAdapter(profileAdapter);
    }

    @Override
    public void onMethodCallback(View v, int minHeight, int h) {
       /* ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1,title);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
        toggleCardViewnHeight(v, minHeight, h);
    }

    private void toggleCardViewnHeight(View v, int minHeight, int height) {

        if (v.getHeight() == minHeight) {
            // expand

            expandView(v, height); //'height' is the height of screen which we have measured already.

        } else {
            // collapse
            collapseView(v, minHeight);

        }
    }

    public void collapseView(final View v, int minHeight) {

        ValueAnimator anim = ValueAnimator.ofInt(v.getMeasuredHeightAndState(),
                minHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = val;
                v.setLayoutParams(layoutParams);

            }
        });
        anim.start();
    }

    public void expandView(final View v, int height) {

        ValueAnimator anim = ValueAnimator.ofInt(v.getMeasuredHeightAndState(),
                height);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = val;
                v.setLayoutParams(layoutParams);
            }
        });
        anim.start();

    }

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++)
        {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia");
            info.setTitle(title);
            result.add(info);
        }

        return result;
    }

    public void Expanding() {

        final int height = 20;
    }


    @Override
    public void setProfile(ProfileInfo profile, ArrayList<Event> eventsList) {
        phoneNumber.setText(profile.getTel());
        email.setText(profile.getEmail());

        Log.d("ramy", profile.getIg());
        Log.d("ramy", profile.getImage());
        /*TODO: Can be removed name is already passed on from previous activity*/
        Log.d("ramy", profile.getName());
        Log.d("ramy", eventsList.get(0).getName());
        Log.d("ramy", eventsList.get(0).getKey());

        profileAdapter.newList(eventsList);
        recyclerList.getAdapter().notifyDataSetChanged();
    }
}