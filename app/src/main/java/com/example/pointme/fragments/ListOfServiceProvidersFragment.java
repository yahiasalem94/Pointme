package com.example.pointme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.Interfaces.MyCallback;
import com.example.pointme.R;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.adapters.ProvidersInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListOfServiceProvidersFragment extends Fragment implements AdapterCallback
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
            serviceProviders = new ArrayList<>();
        } else {
            title = "Pointme";
        }
        context = getContext();

        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<String> value) {
                setAdapter(value);
            }
        });
        // Create recycler view data adapter with car item list.
//        providersAdapter = new ProvidersAdapter(createList(), this);
    }

    public void setAdapter(ArrayList<String> value) {
        List<ProvidersInfo> result = new ArrayList<ProvidersInfo>();
        for (int i = 0; i < value.size(); i++) {
            ProvidersInfo info = new ProvidersInfo();
            Log.d(TAG, value.get(i) + "");
            info.setName(value.get(i));
            info.setSurname("todo");
            info.setEmail("email" + "@test.com");
            result.add(info);
        }
        Log.i(TAG + "test", result.get(0).getName());
        providersAdapter = new ProvidersAdapter(result, this);
        list.setAdapter(providersAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.d(TAG, "hi");
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.activity_list_of_service_providers, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "hi1");

        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbar.setTitle(title);
        list = view.findViewById(R.id.cardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Set data adapter.
        list.setAdapter(providersAdapter);
    }

    public void readData(final MyCallback myCallback) {
        mDatabase.child("ServiceProviders").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                serviceProviders = (ArrayList <String>)dataSnapshot.getKey();
                for (DataSnapshot dataSnap1 : dataSnapshot.getChildren()) {
                    Log.i(TAG, dataSnap1.getKey());
                    serviceProviders.add(dataSnap1.getKey());
                }
                myCallback.onCallback(serviceProviders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMethodCallback(String title) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1,title);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private List<ProvidersInfo> createList(ArrayList<String> value) {

        List<ProvidersInfo> result = new ArrayList<ProvidersInfo>();
        ArrayList<String> y = new ArrayList<String>();
        // Iterator iterator = serviceProviders.entrySet().iterator();
      /*  while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();

            Log.d(TAG, mapEntry.getValue().toString());
            y = (ArrayList<String>)mapEntry.getValue();

            for (int i = 0; i < y.size(); i++)
            {
                ProvidersInfo info = new ProvidersInfo();
                Log.d(TAG, y.get(i)+"");
                info.setName(y.get(i));
                info.setSurname("todo");
                info.setEmail("email"+"@test.com");

                result.add(info);
            }


        }*/
        for (int i = 0; i < value.size(); i++) {
            ProvidersInfo info = new ProvidersInfo();
            Log.d(TAG, value.get(i) + "");
            info.setName(value.get(i));
            info.setSurname("todo");
            info.setEmail("email" + "@test.com");

            result.add(info);
        }

        return result;
    }

}
