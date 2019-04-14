package com.example.pointme.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.interfaces.ListOfSPFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.models.ProvidersInfo;
import com.example.pointme.backendCommunications.DBCom;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListOfServiceProvidersFragment extends Fragment implements RecyclerViewClickListener, ListOfSPFragmentDBInt
{
    private static final String ARG_PARAM1 = "param1";
    private String TAG = "ListOfServiceProvidersFragment";
    private String title;
    private ProvidersAdapter providersAdapter;
    private ArrayList<String> serviceProviders;
    private Context context;
    private DatabaseReference mDatabase;
    RecyclerView list;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            setTitle(title.toUpperCase());
            serviceProviders = new ArrayList<>();
        } else {
            title = "Pointme";
        }
        providersAdapter = new ProvidersAdapter(null, this, getActivity());
        DBCom.getProfilesByService(this, title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_list_of_service_providers, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        list = view.findViewById(R.id.cardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Set data adapter.
        list.setAdapter(providersAdapter);
    }

    public void setTitle(String title) {
        Activity myactivity = getActivity();
        if (myactivity instanceof MainActivity) {
            ((MainActivity) myactivity).setTitle(title);
        }
    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onClickPI(ProfileInfo info) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProfileInfo", info);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setSPList(int serverResult, ArrayList<ProfileInfo> profilesList) {
        providersAdapter.newList(profilesList);
        list.getAdapter().notifyDataSetChanged();
    }
}
