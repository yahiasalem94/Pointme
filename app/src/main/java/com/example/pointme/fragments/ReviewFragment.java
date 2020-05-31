package com.example.pointme.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RatingBar;

import com.example.pointme.R;
import com.example.pointme.adapters.ReviewsAdapter;
import com.example.pointme.decorator.LinearBottomSpacesItemDecoration;
import com.example.pointme.decorator.LinearRightSpacesItemDecoration;
import com.example.pointme.models.Reviews;
import com.example.pointme.viewModels.ReviewsViewModel;
import com.example.pointme.viewModels.ReviewsViewModelFactory;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.pointme.activities.MainActivity.PROFILE_UID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private final String TAG = ReviewFragment.class.getSimpleName();

    private Reviews reviewsModel;
    private ArrayList<Reviews> reviews = new ArrayList<>();
    private String uId;

    private ReviewsViewModel eventsViewModel;
    /* Views */
    private RecyclerView recyclerList;
    private RatingBar qualityRatingBar;
    private RatingBar puncRatingBar;
    private RatingBar attitudeRatingBar;

    private ReviewsAdapter mAdapter;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            uId = getArguments().getString(PROFILE_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootview = inflater.inflate(R.layout.fragment_reviews, container, false);
        recyclerList = mRootview.findViewById(R.id.reviews_recycler_view);
        qualityRatingBar = mRootview.findViewById(R.id.quality_rating);
        puncRatingBar = mRootview.findViewById(R.id.punc_rating);
        attitudeRatingBar = mRootview.findViewById(R.id.attitude_rating);
        mAdapter = new ReviewsAdapter(getActivity());
        setupRecyclerView();

        return mRootview;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerList.setLayoutManager(linearLayoutManager);
        recyclerList.addItemDecoration(new LinearBottomSpacesItemDecoration( 50));


        // Set data adapter.
        recyclerList.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ReviewsViewModelFactory reviewsViewModelFactory = new ReviewsViewModelFactory(uId);
        eventsViewModel = new ViewModelProvider(ReviewFragment.this, reviewsViewModelFactory).get(ReviewsViewModel.class);

        observeReviews();
    }

    private void observeReviews() {

        LiveData<QuerySnapshot> eventsLiveData = eventsViewModel.getDataSnapshotLiveData();
        eventsLiveData.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (dataSnapshot != null) {
//                mProgressBar.setVisibility(View.INVISIBLE);
                for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                    reviewsModel = dataSnapshot.getDocuments().get(i).toObject(Reviews.class);
                    qualityRatingBar.setRating(reviewsModel.getQualityRating());
                    puncRatingBar.setRating(reviewsModel.getPuncRating());
                    attitudeRatingBar.setRating(reviewsModel.getAttitudeRating());
                    reviews.add(reviewsModel);
                }
                mAdapter.setReviews(reviews);
//                runLayoutAnimation(recyclerList);
//                showDataView();
            } else {
//                mProgressBar.setVisibility(View.INVISIBLE);
//                showErrorMessage();
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
