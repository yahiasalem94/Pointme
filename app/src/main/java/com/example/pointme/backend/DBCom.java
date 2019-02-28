package com.example.pointme.backend;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.pointme.Interfaces.CategoriesFragmentDBInt;
import com.example.pointme.Interfaces.ListOfSPFragmentDBInt;
import com.example.pointme.Interfaces.ProfileFragmentDBInt;
import com.example.pointme.models.Event;
import com.example.pointme.models.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBCom {

    public static void getCategoriesList(final CategoriesFragmentDBInt categoriesFragmentDBInt){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> servicesList = new ArrayList<>();

                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    servicesList.add(dataSnap.getKey());
                }
                categoriesFragmentDBInt.setCategoriesList(servicesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getSPList(final ListOfSPFragmentDBInt listOfSPFragmentDBInt, String title){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("ServiceProviders").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> sPList = new ArrayList<>();

                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    sPList.add(dataSnap.getKey());
                }
                listOfSPFragmentDBInt.setSPList(sPList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getProfile(final ProfileFragmentDBInt profileFragmentDBInt, String name){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Names").orderByChild(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = "";
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    uid = dataSnap.getKey();
                }
                final String uid2 = uid;
                mDatabase.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Profile profile = dataSnapshot.getValue(Profile.class);
                        final ArrayList<Event> eventsList = new ArrayList<>();
                        mDatabase.child("Events").child(uid2).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnap : dataSnapshot.getChildren()){
                                    Event event = dataSnap.getValue(Event.class);
                                    event.setKey(dataSnap.getKey());
                                    eventsList.add(event);
                                }
                                profileFragmentDBInt.setProfile(profile, eventsList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
