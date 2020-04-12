package com.example.pointme.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.pointme.constants.ServerResult;
import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.interfaces.ListOfSPFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProvidersAdapter;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.backendCommunications.DBCom;

import java.util.ArrayList;

import static com.example.pointme.activities.MainActivity.nameOfProvider;
import static com.example.pointme.activities.MainActivity.profileInfoTag;

public class ListOfServiceProvidersFragment extends Fragment implements RecyclerViewClickListener, ListOfSPFragmentDBInt
{
    private String TAG = ListOfServiceProvidersFragment.class.getSimpleName();
    private String title;
    private ProvidersAdapter providersAdapter;
    private ArrayList<String> serviceProviders;
    private Context context;
//    private DatabaseReference mDatabase;

    private LinearLayoutManager linearLayoutManager;
    /* View */
    private View mRootview;
    private RecyclerView list;
    private TextView errorTextView;
    private ProgressBar mProgressBar;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = ((MainActivity) getActivity()).toolbar;

        if (getArguments() != null) {
            title = getArguments().getString(nameOfProvider);
            toolbar.setTitle(title.toUpperCase());
            serviceProviders = new ArrayList<>();
        } else {
            title = getString(R.string.Pointme);
        }


        providersAdapter = new ProvidersAdapter(null, this, getActivity());
        DBCom.getProfilesByService(this, title);
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

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onClickPI(ProfileInfo info) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(profileInfoTag, info);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setSPList(int serverResult, ArrayList<ProfileInfo> profilesList) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (serverResult == ServerResult.SUCCESS) {
            providersAdapter.newList(profilesList);
            list.getAdapter().notifyDataSetChanged();
        } else {
            showErrorMessage();
        }
    }
}
