package com.example.pointme.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.viewModels.ProvidersViewModel;
import com.example.pointme.viewModels.ProvidersViewModelFactory;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.pointme.activities.MainActivity.NAME_OF_PROVIDER;

public class ListOfServiceProvidersFragment extends Fragment implements ProvidersAdapter.ProvidersAdapterOnClickHandler {
    private String TAG = ListOfServiceProvidersFragment.class.getSimpleName();
    private String service;
    private ProvidersAdapter providersAdapter;
    private ArrayList<ServiceProvider> serviceProviders;
    private Context context;

    private LinearLayoutManager linearLayoutManager;

    /* View */
    private View mRootview;
    private RecyclerView list;
    private TextView errorTextView;
    private ProgressBar mProgressBar;
    private Toolbar toolbar;

    private ServiceProvider serviceProvidersModel;

    private ProvidersViewModelFactory providersViewModelFactory;
    private ProvidersViewModel providersViewModel;
    private LiveData<QuerySnapshot> liveData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        toolbar = ((MainActivity) getActivity()).toolbar;

        if (getArguments() != null) {
            service = getArguments().getString(NAME_OF_PROVIDER);
            toolbar.setTitle(service.toUpperCase());

            providersViewModelFactory = new ProvidersViewModelFactory(service);
            providersViewModel = new ViewModelProvider(ListOfServiceProvidersFragment.this, providersViewModelFactory).get(ProvidersViewModel.class);
        } else {
            toolbar.setTitle(getString(R.string.Pointme).toUpperCase());
        }

        serviceProviders = new ArrayList<>();
        providersAdapter = new ProvidersAdapter(getActivity(), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        mRootview = inflater.inflate(R.layout.fragment_list_of_service_providers, parent, false);

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
        liveData = providersViewModel.getDataSnapshotLiveData();

        liveData.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (dataSnapshot != null) {
                mProgressBar.setVisibility(View.INVISIBLE);
                for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                    serviceProvidersModel = dataSnapshot.getDocuments().get(i).toObject(ServiceProvider.class);
                    serviceProviders.add(serviceProvidersModel);
                }
                providersAdapter.setProvidersData(serviceProviders);
                showDataView();
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                showErrorMessage();
            }
        });
    }

    private void setupRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        // Set data adapter.
        list.setAdapter(providersAdapter);
    }

    private void showDataView() {
        /* First, make sure the error is invisible */
        errorTextView.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        list.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        list.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorTextView.setVisibility(View.VISIBLE);
    }


//    @Override
//    public void onClickPI(ProfileInfo info) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(PROFILE_INFO, info);
//        fragment.setArguments(bundle);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    @Override
    public void onClick(int position) {

    }
}
