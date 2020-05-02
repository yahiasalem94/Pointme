package com.example.pointme.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.models.ServiceProvider;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private String TAG = ProfileFragment.class.getSimpleName();
    private ProfileAdapter profileAdapter;
    private String name;
    private ServiceProvider profileInfo;

    private String phoneNumber = "tel:";
    private String instagramLink;

    /*Views*/
    private CircleImageView profileImage;
    private LinearLayout phoneLinearLayout;
    private LinearLayout instagramLinearLayout;
    private RecyclerView recyclerList;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = getArguments().getParcelable(PROFILE_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View mRootview = inflater.inflate(R.layout.fragment_profile, parent, false);
        // Defines the xml file for the fragment
        loadFragment();

        /* Initalizing views */
        profileImage = mRootview.findViewById(R.id.ivProfile);
        TextView nameView = mRootview.findViewById(R.id.tvName);
        phoneLinearLayout = mRootview.findViewById(R.id.callLayout);
        instagramLinearLayout = mRootview.findViewById(R.id.instagramLayout);


        phoneLinearLayout.setOnClickListener(this);
        phoneLinearLayout.setClickable(false);

        instagramLinearLayout.setOnClickListener(this);
        instagramLinearLayout.setClickable(false);

        nameView.setText(profileInfo.getName());

        addLinks();


        final GestureDetector gesture = new GestureDetector(getActivity(),
                new SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i(TAG, "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(TAG, "Right to Left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(TAG, "Left to Right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        mRootview.setOnTouchListener((v, event) -> gesture.onTouchEvent(event));
        return mRootview;
    }

    private void addLinks() {

        if (profileInfo.getTel() != null) {
            phoneNumber += profileInfo.getTel();
            phoneLinearLayout.setClickable(true);
        }

        if (profileInfo.getIg() != null) {
            instagramLink = profileInfo.getIg();
            instagramLinearLayout.setClickable(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).toolbar;
        toolbar.setTitle(null);
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

    private void callServiceProvider() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }

    private void openInstagram() {
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
            Toast.makeText(getActivity(), "User doesn't have instagram account", Toast.LENGTH_LONG).show();
        }
    }

    private void loadFragment() {
        Log.d(TAG, "loading fragment");
        EventsFragment fragment = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PROFILE_INFO, profileInfo);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}