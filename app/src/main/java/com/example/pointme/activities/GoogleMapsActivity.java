package com.example.pointme.activities;


import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.pointme.R;
import com.example.pointme.utils.DoubleArrayEvaluator;
import com.example.pointme.utils.LocationTrack;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.example.pointme.fragments.BookingConfirmationFragment.REQUEST_CODE;


public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, View.OnClickListener {

    private static final String TAG = GoogleMapsActivity.class.getSimpleName();
    public static final String ADDRESS = "Address";
    private GoogleMap mMap;
    private Marker marker;
    String address;
    private boolean isPlaceSearched;

    private MaterialButton mSaveAddressButton;
    AutocompleteSupportFragment placeAutoComplete;


    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        mSaveAddressButton = findViewById(R.id.save_Address);
        mSaveAddressButton.setOnClickListener(this);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        Places.initialize(getApplicationContext(), "AIzaSyB6EqqJzl8EIS5CYOO7ZpWWToSh_pEF0Uw");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        placeAutoComplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete);

        getLocationPermission();

        //initialize autocomplete
        initAutoComplete();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationTrack = new LocationTrack(this);
    }

    private void getLocation() {
        if (mLocationPermissionsGranted) {
            if (locationTrack.canGetLocation()) {
                moveCamera(new LatLng(locationTrack.getLatitude(), locationTrack.getLongitude()), 15f);
                marker = mMap.addMarker(new MarkerOptions().position(new LatLng(locationTrack.getLatitude(), locationTrack.getLongitude())).draggable(true));
                Log.d(TAG, locationTrack.getLatitude()+"" + " " + locationTrack.getLongitude()+"");
            } else {
                locationTrack.showSettingsAlert();
            }
        } else {
            getLocationPermission();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        LatLng center = new LatLng(40.76793169992044,
                -73.98180484771729);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(center, 17f);
        mMap.animateCamera(cameraUpdate);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnMarkerDragListener(this);

        if (mLocationPermissionsGranted) {
//            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            getLocation();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d("GoogleMapsActivity", marker.getPosition().latitude + " ");
        moveCamera(marker.getPosition(), 15f);
        isPlaceSearched = false;
    }


    private void initAutoComplete() {
//        // Initialize Places.
        // Specify the types of place data to return.
        placeAutoComplete.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                moveCamera(place.getLatLng(), 15f);

                if (marker != null) {
                    double[] startValues = new double[]{marker.getPosition().latitude, marker.getPosition().longitude};
                    double[] endValues = new double[]{place.getLatLng().latitude, place.getLatLng().longitude};
                    ValueAnimator latLngAnimator = ValueAnimator.ofObject(new DoubleArrayEvaluator(), startValues, endValues);
                    latLngAnimator.setDuration(800);
                    latLngAnimator.setInterpolator(new DecelerateInterpolator());
                    latLngAnimator.addUpdateListener(animation -> {
                        double[] animatedValue = (double[]) animation.getAnimatedValue();
                        marker.setPosition(new LatLng(animatedValue[0], animatedValue[1]));
                    });
                    latLngAnimator.start();
                } else {

                    marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).draggable(true));
                }
                Log.d("Maps", "Place selected: " + place.getName());
                isPlaceSearched = true;
                address = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });
    }

//    private void getDeviceLocation(){
//        Log.d(TAG, "getDeviceLocation: getting the devices current location");
//
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try{
//            if(mLocationPermissionsGranted){
//
//                final Task location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
//                        Log.d(TAG, "onComplete: found location!");
//                        Location currentLocation = (Location) task.getResult();
//
//
//                    }else{
//                        Log.d(TAG, "onComplete: current location is null");
//                        Toast.makeText(GoogleMapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        } catch (SecurityException e){
//            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
//        }
//    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();

                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (!isPlaceSearched) {
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            String streetAddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            address  = streetAddress;
        }

        Intent intent=new Intent();
        intent.putExtra(ADDRESS,address);
        setResult(REQUEST_CODE,intent);
        finish();//finishing activity
    }
}