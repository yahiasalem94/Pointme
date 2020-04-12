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
import com.example.pointme.models.ProfileInfo;

import static com.example.pointme.activities.MainActivity.profileInfoTag;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private String TAG = ProfileFragment.class.getSimpleName();
    private ProfileAdapter profileAdapter;
    private static final String ARG_PARAM1 = "param1";
    private String name;
    private ProfileInfo profileInfo;

    private String phoneNumber = "tel:";
    private String instagramLink;

    /*Views*/
    private ImageView profileImage;
    private LinearLayout phoneLinearLayout;
    private LinearLayout instagramLinearLayout;
    private RecyclerView recyclerList;
    private TextView nameView;
    private Toolbar toolbar;
    private View mRootview;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = (ProfileInfo) getArguments().getSerializable(profileInfoTag);
        }

        toolbar = ((MainActivity) getActivity()).toolbar;
        toolbar.setTitle("");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        mRootview = inflater.inflate(R.layout.fragment_profile, parent, false);
        // Defines the xml file for the fragment
        loadFragment();

        profileImage = (de.hdodenhof.circleimageview.CircleImageView) mRootview.findViewById(R.id.ivProfile);
        nameView = mRootview.findViewById(R.id.tvName);
        phoneLinearLayout = mRootview.findViewById(R.id.callLayout);
        instagramLinearLayout = mRootview.findViewById(R.id.instagramLayout);

        phoneLinearLayout.setOnClickListener(this);
        instagramLinearLayout.setOnClickListener(this);
        nameView.setText(profileInfo.getName());
        phoneNumber += profileInfo.getTel();
        instagramLink = profileInfo.getIg();

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

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

        mRootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return mRootview;
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

    public void loadFragment() {
        Log.d(TAG, "loading fragment");
        EventsFragment fragment = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProfileInfo", profileInfo);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}