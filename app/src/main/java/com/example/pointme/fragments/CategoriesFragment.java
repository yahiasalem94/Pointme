package com.example.pointme.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pointme.Interfaces.RecyclerViewClickListener;
import com.example.pointme.Interfaces.CategoriesFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.CategoriesAdapter;
import com.example.pointme.decorator.GridSpacingItemDecorator;
import com.example.pointme.models.CategoriesItem;
import com.example.pointme.backendCommunications.DBCom;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements RecyclerViewClickListener, CategoriesFragmentDBInt {

    private Toolbar toolbar;

    private List<CategoriesItem> categoriesList = null;

    private CategoriesAdapter categoriesAdapter;
    private ArrayList<String> serviceProviders;
    private String TAG = "CategoriesFragment";
    private static final String ARG_PARAM1 = "param1";

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceProviders = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(null, this);
//        initializeList();
        DBCom.getCategoriesList(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_categories, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Create the recyclerview.
        recyclerView = view.findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 2 columns.
        gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecorator(getActivity(), 2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Set data adapter.
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onClick(String title) {
        ListOfServiceProvidersFragment fragment = new ListOfServiceProvidersFragment();
        Log.d(TAG, title);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, title);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setCategoriesList(ArrayList<String> servicesList) {
        categoriesList = new ArrayList<>();
        for (int i = 0; i < servicesList.size(); i++) {
            switch (servicesList.get(i)) {
                case "Crossfit":
                    Log.i(TAG, "Adding category crossfit");
                    categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.crossfit_1));
                    break;
                case "Yoga":
                    Log.i(TAG, "Adding category yoga");
                    categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.yoga_1));
                    break;
                case "Hairdressers":
                    Log.i(TAG, "Adding category hairdressers");
                    categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.hairdresser_1));
                    break;
                case "Makeup":
                    Log.i(TAG, "Adding category makeup");
                    categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.makeup_1));
                    break;
                case "Swimming":
                    Log.i(TAG, "Adding category swimming");
                    categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.swimming_pool_1));
                    break;
            }
        }
        categoriesAdapter.newList(categoriesList);
        //recyclerView.getAdapter().notifyDataSetChanged();
    }
}