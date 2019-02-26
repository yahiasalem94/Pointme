package com.example.pointme.backend;

import android.support.annotation.NonNull;

import com.example.pointme.Interfaces.CategoriesFragmentDBInt;
import com.example.pointme.fragments.CategoriesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesFragmentDB {

    public static void getCategoriesList(final CategoriesFragmentDBInt categoriesFragmentDBInt){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                serviceProviders = (ArrayList <String>)dataSnapshot.getKey();
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
}
