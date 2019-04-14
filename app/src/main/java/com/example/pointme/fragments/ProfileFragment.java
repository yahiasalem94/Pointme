package com.example.pointme.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.interfaces.ProfileAdapterCallback;
import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private String TAG = "ProfileFragment";
    private ProfileAdapter profileAdapter;
    private static final String ARG_PARAM1 = "param1";
    private String name;
    private ProfileInfo profileInfo;
    private String phoneNumber = "tel:";
    private String instagramLink;
    /*Views*/
    private LinearLayout phoneLinearLayout;
    private LinearLayout instagramLinearLayout;
    private RecyclerView recyclerList;
    private TextView nameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = (ProfileInfo) getArguments().getSerializable("ProfileInfo");
        } else {
            name = "Yahia";
        }
        setTitle();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        loadFragment();
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        recyclerList = view.findViewById(R.id.card_view_list);
        nameView = view.findViewById(R.id.tvName);
        phoneLinearLayout = view.findViewById(R.id.callLayout);
        instagramLinearLayout = view.findViewById(R.id.instagramLayout);

        phoneLinearLayout.setOnClickListener(this);
        instagramLinearLayout.setOnClickListener(this);
        nameView.setText(profileInfo.getName());
        phoneNumber += profileInfo.getTel();
        instagramLink = profileInfo.getIg();

//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerList.setLayoutManager(linearLayoutManager);
        // recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));

        // Set data adapter.
//        recyclerList.setAdapter(profileAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.callLayout:
                callServiceProvider();
                break;
            case R.id.instagramLayout:
                openInstagram();
                break;
            default:
                break;
        }
    }

    public void setTitle() {
        Activity myactivity = getActivity();
        if (myactivity instanceof MainActivity) {
            ((MainActivity) myactivity).setTitle("");
        }
    }

    public void callServiceProvider() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }

    public void openInstagram() {
        if (!instagramLink.isEmpty()) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://instagram.com/_u/" + instagramLink));
                intent.setPackage("com.instagram.android");
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/" + instagramLink)));
            }
        } else {
            Toast.makeText(getApplicationContext(), "User doesn't have instagram account", Toast.LENGTH_LONG).show();
        }
    }

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++)
        {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia");
            info.setTitle(name);
            result.add(info);
        }

        return result;
    }

    public void loadFragment() {
        /* TODO Event fragment is being loaded from here */
        // load fragment
        Log.d(TAG, "loading fragment");
        EventsFragment fragment = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProfileInfo", profileInfo);
        //bundle.putString("SpId", profileInfo.getKey());
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}