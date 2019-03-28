package com.example.pointme.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.pointme.Interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.adapters.FavoritesAdapter;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.models.ProvidersInfo;
import com.example.pointme.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements RecyclerViewClickListener {

    private Toolbar toolbar;
    private FavoritesAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String title;
    private SharedPreference sharedPreference;
    private List<ProvidersInfo> favorites;
    private String TAG = "FavoriteFragment";

    private RecyclerView list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        } else {
            title = "Pointme";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment


        return inflater.inflate(R.layout.fragment_favorites, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Get favorite items from SharedPreferences.
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(getActivity());

        list = view.findViewById(R.id.cardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        if (favorites == null) {
            showAlert("No Items", "Add service providers to your list");
        } else {
            if (favorites.size() == 0) {
                showAlert("No Items", "Add service providers to your list");
            } else {
                adapter = new FavoritesAdapter(favorites, this);
                // Set data adapter.
                list.setAdapter(adapter);
            }
        }
    }

    public void showAlert(String title, String message) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // activity.finish();
                    list.setAdapter(null);
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void onClick(String title) {

        ProfileFragment fragment = new ProfileFragment();
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
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Favorites");
    }

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia" + i);
            info.setTitle(title);
            result.add(info);
        }

        return result;
    }
}