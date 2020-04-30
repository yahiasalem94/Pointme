package com.example.pointme.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pointme.R;
import com.example.pointme.adapters.CategoriesAdapter;
import com.example.pointme.decorator.GridSpacingItemDecorator;
import com.example.pointme.models.CategoriesModel;
import com.example.pointme.viewModels.CategoriesViewModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.pointme.activities.MainActivity.NAME_OF_PROVIDER;

public class CategoriesFragment extends Fragment implements CategoriesAdapter.CategoriesAdapterOnClickHandler {

    private Toolbar toolbar;

    private List<CategoriesModel> categoriesList = null;
    private CategoriesModel categoriesModel;

    private CategoriesAdapter categoriesAdapter;
    private String TAG = "CategoriesFragment";

    private CategoriesViewModel categoriesViewModel;
    private LiveData<DocumentSnapshot> liveData;

    /* Views */
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private View mRootview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesAdapter = new CategoriesAdapter(getActivity(), this);
//        DBCom.getCategoriesList(this);

        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liveData = categoriesViewModel.getDataSnapshotLiveData();

        liveData.observe(getViewLifecycleOwner(), new Observer<DocumentSnapshot>() {
            @Override
            public void onChanged(@Nullable DocumentSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        // update the UI here with values in the snapshot
                        Log.d(TAG, dataSnapshot.toString());
                        categoriesModel = dataSnapshot.toObject(CategoriesModel.class);
                        categoriesAdapter.setCategoriesData(categoriesModel.getImageUrls());
                    }
                }
            }
        });
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
    public void onClick(int position) {
        Log.d(TAG, "CategoryName");
        ListOfServiceProvidersFragment fragment = new ListOfServiceProvidersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME_OF_PROVIDER, categoriesModel.getCategoriesNames().get(position));
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}