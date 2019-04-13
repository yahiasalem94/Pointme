package com.example.pointme.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.interfaces.ProfileAdapterCallback;
import com.example.pointme.interfaces.ProfileFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.backendCommunications.DBCom;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.pointme.decorator.CardViewAnimation.*;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment implements ProfileAdapterCallback, ProfileFragmentDBInt, View.OnClickListener {

    private String TAG = "ProfileFragment";
    private ProfileAdapter profileAdapter;
    private static final String ARG_PARAM1 = "param1";
    private String name;
    private String phoneNumber = "tel:";
    private String instagramLink;
    /*Views*/
    private LinearLayout phoneLinearLayout;
    private LinearLayout instagramLinearLayout;
    private RecyclerView recyclerList;
    private TextView nameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        } else {
            name = "Yahia";
        }
        setTitle();
        profileAdapter = new ProfileAdapter(null, this, getActivity());

        DBCom.getProfile(this, name);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        loadFragment();
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        recyclerList = view.findViewById(R.id.card_view_list);
        nameView = view.findViewById(R.id.tvName);
        phoneLinearLayout = view.findViewById(R.id.callLayout);
        instagramLinearLayout = view.findViewById(R.id.instagramLayout);

        phoneLinearLayout.setOnClickListener(this);
        instagramLinearLayout.setOnClickListener(this);
        nameView.setText(name);

//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerList.setLayoutManager(linearLayoutManager);
        // recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));

        // Set data adapter.
//        recyclerList.setAdapter(profileAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.callLayout:
                callServiceProvider();
                break;
            case R.id.instagramLayout:
                openInstagram();
                break;
            default:
                break;
        }
    }

    public void setTitle() {
        Activity myactivity = getActivity();
        if (myactivity instanceof MainActivity) {
            ((MainActivity) myactivity).setTitle("");
        }
    }

    public void callServiceProvider() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }

    public void openInstagram() {
        if (!instagramLink.isEmpty()) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://instagram.com/_u/" + instagramLink));
                intent.setPackage("com.instagram.android");
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/" + instagramLink)));
            }
        } else {
            Toast.makeText(getApplicationContext(), "User doesn't have instagram account", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMethodCallback(View v, int minHeight, int h) {
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

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++)
        {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia");
            info.setTitle(name);
            result.add(info);
        }

        return result;
    }

    @Override
    public void setProfile(ProfileInfo profile, ArrayList<Event> eventsList) {
        phoneNumber += profile.getTel();
        instagramLink = profile.getIg();
//        Log.d("ramy", profile.getIg());
        //      Log.d("ramy", profile.getImage());
        /*TODO: Can be removed name is already passed on from previous activity*/
        //   Log.d("ramy", profile.getName());
//        Log.d("ramy", eventsList.get(0).getName());
//        Log.d("ramy", eventsList.get(0).getKey());
//        Event e = new Event();
//        e.setName("workshop");
//        e.setDesc("this is a workshop");
//        Event e1 = new Event();
//        e1.setName("workshop");
//        e1.setDesc("this is a workshop");
//        eventsList.add(e);
//        eventsList.add(e1);
//        profileAdapter.newList(eventsList);
//        recyclerList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setSPEventsAndAppointments(int serverResult, ArrayList<Event> eventsList, ArrayList<Appointment> appointmentsList) {

    }

    public void loadFragment() {
        // load fragment
        Log.d(TAG, "loading fragment");
//        EventsFragment fragment = new EventsFragment();
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, name);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}