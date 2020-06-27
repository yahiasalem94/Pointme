package com.example.pointme.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.request.RequestOptions;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.GlideApp;
import com.example.pointme.utils.SharedPreference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.PROFILE_UID;

public class ProvidersProfileFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        ActionBottomDialogFragment.BottomSheetListener {

    private String TAG = ProfileFragment.class.getSimpleName();
    private ProfileAdapter profileAdapter;
    private String name;
    private ServiceProvider profileInfo;

    private String phoneNumber = "tel:";
    private String instagramLink;

    private SharedPreference sharedPreference;

    /*Views*/
    private CircleImageView profileImage;
    private LinearLayout phoneLinearLayout;
    private LinearLayout instagramLinearLayout;
    private RatingBar mRatingBar;
    private LinearLayout eventLinearLayout;
    private LinearLayout reviewsLinearLayout;
    private TextView eventsTv;
    private TextView reviewsTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = getArguments().getParcelable(PROFILE_INFO);
        }
        sharedPreference = new SharedPreference();

    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("My Profile");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View mRootview = inflater.inflate(R.layout.fragment_provider_profile, parent, false);
        // Defines the xml file for the fragment

        /* Initalizing views */
        profileImage = mRootview.findViewById(R.id.ivProfile);
        TextView nameView = mRootview.findViewById(R.id.tvName);
        phoneLinearLayout = mRootview.findViewById(R.id.callLayout);
        instagramLinearLayout = mRootview.findViewById(R.id.instagramLayout);
        mRatingBar = mRootview.findViewById(R.id.rating);
        TextView numOfReviews = mRootview.findViewById(R.id.numReviews);
        eventsTv = mRootview.findViewById(R.id.eventTv);
        reviewsTv = mRootview.findViewById(R.id.reviewsTv);
        eventLinearLayout = mRootview.findViewById(R.id.eventsLinearLayout);
        reviewsLinearLayout = mRootview.findViewById(R.id.reviewsLinearLayout);

        profileImage.setOnClickListener(this);

        eventLinearLayout.setOnClickListener(this);
        eventLinearLayout.setClickable(false);

        reviewsLinearLayout.setOnClickListener(this);
        reviewsLinearLayout.setClickable(true);


        phoneLinearLayout.setOnClickListener(this);
        phoneLinearLayout.setClickable(false);

        instagramLinearLayout.setOnClickListener(this);
        instagramLinearLayout.setClickable(false);

        GlideApp.with(this)
                .load(profileInfo.getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .fitCenter())
                .into(profileImage);

        nameView.setText(profileInfo.getName());
        mRatingBar.setRating(profileInfo.getAvgRating());
        numOfReviews.setText("(" + profileInfo.getNumReviews() + ")");
        addLinks();

        loadEventFragment();

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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.callLayout:
                callServiceProvider();
                break;
            case R.id.instagramLayout:
                openInstagram();
                break;
            case R.id.eventsLinearLayout:
                reviewsLinearLayout.setBackgroundResource(0);
                eventLinearLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.capsule_white_background));
                eventsTv.setTextColor(Color.BLACK);
                reviewsTv.setTextColor(Color.WHITE);
                loadEventFragment();
                eventLinearLayout.setClickable(false);
                reviewsLinearLayout.setClickable(true);
                break;
            case R.id.reviewsLinearLayout:
                eventLinearLayout.setBackgroundResource(0);
                reviewsLinearLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.capsule_white_background));
                reviewsTv.setTextColor(Color.BLACK);
                eventsTv.setTextColor(Color.WHITE);
                loadReviewsFragment();
                reviewsLinearLayout.setClickable(false);
                eventLinearLayout.setClickable(true);
                break;
            case R.id.ivProfile:
                selectImage();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        //animation
        compoundButton.startAnimation(scaleAnimation);

        if (isChecked) {
            sharedPreference.addFavorite(getActivity(), profileInfo);
            Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_LONG).show();
        } else {
            sharedPreference.removeFavorite(getActivity(), profileInfo);
            Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_LONG).show();
        }
    }

    private void callServiceProvider() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profileImage.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = Objects.requireNonNull(getActivity()).getApplicationContext().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void selectImage() {
        ActionBottomDialogFragment addPhotoBottomDialogFragment = ActionBottomDialogFragment.newInstance();
        addPhotoBottomDialogFragment.show(this.getChildFragmentManager(), ActionBottomDialogFragment.TAG);
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

    private void loadEventFragment() {
        Log.d(TAG, "loading fragment");
        EventsFragment fragment = new EventsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(PROFILE_INFO, profileInfo);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void loadReviewsFragment() {
        Log.d(TAG, "loading fragment");
        ReviewFragment fragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PROFILE_UID, profileInfo.getuID());
        fragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right, R.anim.slide_from_right, R.anim.slide_to_left);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }


    @Override
    public void onBottomSheetItemClick(int id) {
        switch (id) {
            case R.id.gallery:
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
                break;
            case R.id.camera:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
                break;
            case R.id.cancel:
                /* fallthrough */
                break;
            default:
                break;
        }
    }
}