package com.example.pointme.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.models.CategoriesModel;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.SharedPreference;
import com.example.pointme.viewModels.ProvidersViewModel;
import com.example.pointme.viewModels.ProvidersViewModelFactory;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.pointme.activities.MainActivity.NAME_OF_PROVIDER;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;

public class ListOfServiceProvidersFragment extends Fragment implements ProvidersAdapter.ProvidersAdapterOnClickHandler,  ProvidersAdapter.FavoriteOnClickHandler {
    private String TAG = ListOfServiceProvidersFragment.class.getSimpleName();
    private ProvidersAdapter providersAdapter;
    private ArrayList<ServiceProvider> serviceProviders;
    private SharedPreference sharedPreference;

    private String service;

    private RecyclerView list;
    private TextView errorTextView;
    private ProgressBar mProgressBar;

    private ServiceProvider serviceProvidersModel;

    private ProvidersViewModel providersViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        sharedPreference = new SharedPreference();

        if (getArguments() != null) {
            service = getArguments().getString(NAME_OF_PROVIDER);


            ProvidersViewModelFactory providersViewModelFactory = new ProvidersViewModelFactory(service);
            providersViewModel = new ViewModelProvider(ListOfServiceProvidersFragment.this, providersViewModelFactory).get(ProvidersViewModel.class);
        }

        serviceProviders = new ArrayList<>();
        providersAdapter = new ProvidersAdapter(getActivity(), sharedPreference,this, this);

    }

    @Override
    public void onStart() {
        super.onStart();
        assert service != null;
        getActivity().setTitle(service);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        /* View */
        View mRootview = inflater.inflate(R.layout.fragment_list_of_service_providers, parent, false);

        list = mRootview.findViewById(R.id.cardList);
        errorTextView = mRootview.findViewById(R.id.tv_error_message_display);
        mProgressBar = mRootview.findViewById(R.id.pb_loading_indicator);

        setupRecyclerView();

        return mRootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar.setVisibility(View.VISIBLE);
        LiveData<QuerySnapshot> liveData = providersViewModel.getDataSnapshotLiveData();

        liveData.observe(getViewLifecycleOwner(), new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot dataSnapshot) {
                Log.d(TAG, "[OnChanged]: " + hashCode());
                mProgressBar.setVisibility(View.GONE);
                if (dataSnapshot != null) {
                    serviceProviders.clear();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                        serviceProvidersModel = dataSnapshot.getDocuments().get(i).toObject(ServiceProvider.class);
                        assert serviceProvidersModel != null;
                        serviceProvidersModel.setuID(dataSnapshot.getDocuments().get(i).getId());
                        serviceProviders.add(serviceProvidersModel);
                    }
                    providersAdapter.setProvidersData(serviceProviders);
                } else {
                    showErrorMessage();
                }
            }

        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        // Set data adapter.
        list.setAdapter(providersAdapter);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        list.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PROFILE_INFO, serviceProviders.get(position));
        fragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFavoriteClick(CompoundButton compoundButton, boolean isChecked, int position) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        //animation
        compoundButton.startAnimation(scaleAnimation);

        if (isChecked) {
            sharedPreference.addFavorite(getActivity(), serviceProviders.get(position));
            Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_LONG).show();
        } else {
            sharedPreference.removeFavorite(getActivity(), serviceProviders.get(position));
            Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_LONG).show();
        }
    }
}
