package com.example.pointme.fragments;

import android.os.Bundle;
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
import com.example.pointme.R;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.adapters.ProvidersInfo;

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
    private HashMap<String, ArrayList<String>> serviceProviders;
   @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            serviceProviders = (HashMap<String, ArrayList<String>>) getArguments().getSerializable("Hashmap");
        } else {
            title = "Pointme";
        }
        Log.i(TAG, serviceProviders.toString());
        // Create recycler view data adapter with car item list.
        providersAdapter = new ProvidersAdapter(createList(), this);
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
        RecyclerView list = view.findViewById(R.id.cardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Set data adapter.
        list.setAdapter(providersAdapter);
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

    private List<ProvidersInfo> createList() {

        List<ProvidersInfo> result = new ArrayList<ProvidersInfo>();
        ArrayList<String> y = new ArrayList<String>();
        Iterator iterator = serviceProviders.entrySet().iterator();
        while (iterator.hasNext()) {
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


        }

        return result;
    }

}
