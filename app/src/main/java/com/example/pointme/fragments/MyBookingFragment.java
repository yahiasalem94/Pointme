package com.example.pointme.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.pointme.adapters.BookingsAdapter;
import com.example.pointme.backendCommunications.DBCom;
import com.example.pointme.interfaces.ClientBookingsDBInt;
import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.adapters.FavoritesAdapter;
import com.example.pointme.models.Booking;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.models.ProvidersInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MyBookingFragment extends Fragment implements RecyclerViewClickListener, ClientBookingsDBInt {

    private String title = "Bookings";
    private String currentUser;
    private Toolbar toolbar;
    private BookingsAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String TAG = "BookingsFragment";
    /*views*/
    RecyclerView list;
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new BookingsAdapter(null, this);
        DBCom.getClientBookings(this, currentUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_my_booking, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);

        list = view.findViewById(R.id.cardList);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Set data adapter.
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onClickPI(ProfileInfo profileInfo) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Bookings");
    }

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia");
            result.add(info);
        }

        return result;
    }

    @Override
    public void setClientBookings(int serverResult, ArrayList<Booking> bookingsList) {
        adapter.newList(bookingsList);
        list.getAdapter().notifyDataSetChanged();
    }
}