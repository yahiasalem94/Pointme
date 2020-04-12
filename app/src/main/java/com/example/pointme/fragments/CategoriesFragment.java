package com.example.pointme.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.interfaces.CategoriesFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.CategoriesAdapter;
import com.example.pointme.decorator.GridSpacingItemDecorator;
import com.example.pointme.models.CategoriesItem;
import com.example.pointme.backendCommunications.DBCom;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.pointme.activities.MainActivity.nameOfProvider;

public class CategoriesFragment extends Fragment implements RecyclerViewClickListener, CategoriesFragmentDBInt {

    private Toolbar toolbar;

    private List<CategoriesItem> categoriesList = null;

    private CategoriesAdapter categoriesAdapter;
    private ArrayList<String> serviceProviders;
    private String TAG = "CategoriesFragment";


    /* Views */
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private View mRootview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceProviders = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(null, this);
        DBCom.getCategoriesList(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        mRootview = inflater.inflate(R.layout.fragment_categories, parent, false);
        recyclerView = mRootview.findViewById(R.id.card_view_recycler_list);

        // Create the recyclerview.
        setupRecyclerView();

        return mRootview;
    }

    private void setupRecyclerView() {
        // Create the grid layout manager with 2 columns.
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecorator(getActivity(), 2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Set data adapter.
        recyclerView.setAdapter(categoriesAdapter);
    }


    @Override
    public void onClick(String title) {
        Log.d(TAG, title);
        ListOfServiceProvidersFragment fragment = new ListOfServiceProvidersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(nameOfProvider, title);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClickPI(ProfileInfo profileInfo) {

    }

    @Override
    public void setCategoriesList(ArrayList<String> servicesList) {
        categoriesList = new ArrayList<>();
        for (int i = 0; i < servicesList.size(); i++) {
            if (servicesList.get(i).equals(getString(R.string.crossfit))) {
                Log.i(TAG, "Adding category crossfit");
                categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.crossfit_1));
            } else if (servicesList.get(i) .equals( getString(R.string.Yoga))) {
                Log.i(TAG, "Adding category yoga");
                categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.yoga_1));
            } else if (servicesList.get(i) .equals( getString(R.string.Hairdressers))) {
                Log.i(TAG, "Adding category hairdressers");
                categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.hairdresser_1));
            } else if (servicesList.get(i) .equals( getString(R.string.Makeup))) {
                categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.makeup_1));
            } else if (servicesList.get(i) .equals( getString(R.string.Swimming))) {
                categoriesList.add(new CategoriesItem(servicesList.get(i), R.drawable.swimming_pool_1));
            }
        }
        categoriesAdapter.newList(categoriesList);
    }
}