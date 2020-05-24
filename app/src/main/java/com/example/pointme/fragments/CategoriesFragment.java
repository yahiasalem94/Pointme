package com.example.pointme.fragments;

import android.app.ProgressDialog;
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

    private String TAG = CategoriesFragment.class.getSimpleName();

    private CategoriesModel categoriesModel;
    private CategoriesAdapter categoriesAdapter;
    private CategoriesViewModel categoriesViewModel;

    /* Views */
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        categoriesAdapter = new CategoriesAdapter(getActivity(), this);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart");
        getActivity().setTitle(R.string.categories);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "OnDestroyView");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View mRootview = inflater.inflate(R.layout.fragment_categories, parent, false);
        recyclerView = mRootview.findViewById(R.id.card_view_recycler_list);

        // Create the recyclerview.
        setupRecyclerView();


        return mRootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = ProgressDialog.show(getActivity(),
                "ProgressDialog", "Fetching Categories");

        LiveData<DocumentSnapshot> liveData = categoriesViewModel.getDataSnapshotLiveData();

        liveData.observe(getViewLifecycleOwner(), new Observer<DocumentSnapshot>() {
            @Override
            public void onChanged(@Nullable DocumentSnapshot dataSnapshot) {
                Log.d(TAG, "[OnChanged]: " + hashCode());

                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        progressDialog.dismiss();
                        // update the UI here with values in the snapshot
                        categoriesModel = dataSnapshot.toObject(CategoriesModel.class);
                        categoriesAdapter.setCategoriesData(categoriesModel.getCategoriesNames(), categoriesModel.getImageUrls());
                    }
                }
            }
        });
    }

    private void setupRecyclerView() {
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

//        recyclerView.addItemDecoration(new GridSpacingItemDecorator(getActivity(), 2, 10, true));
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