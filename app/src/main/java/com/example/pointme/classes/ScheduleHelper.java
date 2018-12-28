package com.example.pointme.classes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ScheduleHelper {

    public static void uploadEvent(FirebaseAuth auth, DatabaseReference database, Event event){
        String eventKey = database.child(Constants.EVENTS).child(auth.getCurrentUser().getUid()).push().getKey();
        Map<String, Object> eventMap = new HashMap<>();

        eventMap.put(Constants.EVENTSPATH + auth.getCurrentUser().getUid() + "/" + eventKey, event);
        eventMap.put(Constants.DATESPATH + auth.getCurrentUser().getUid() + "/" + eventKey, event.getDates());

        database.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Ramy", "SUCCESS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Ramy", "FAILURE");
            }
        });
    }

    public static void uploadAppointment(FirebaseAuth auth, DatabaseReference database, Appointment appointment){
        String appointmentKey = database.child(Constants.APPOINTMENTS).child(auth.getCurrentUser().getUid()).push().getKey();
        Map<String, Object> eventMap = new HashMap<>();

        eventMap.put(Constants.APPOINTMENTSPATH + auth.getCurrentUser().getUid() + "/" + appointmentKey, appointment);
        eventMap.put(Constants.DATESPATH + auth.getCurrentUser().getUid() + "/" + appointmentKey, appointment.getDates());

        database.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Ramy", "SUCCESS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Ramy", "FAILURE");
            }
        });
    }

    public static void uploadBooking(final FirebaseAuth auth, final DatabaseReference database, @Constants.ScheduleType int type, String name, final Booking booking){
        Query query = null;
        if (type == Constants.EVENTTYPE){
            query = database.child(Constants.EVENTS).child(auth.getCurrentUser().getUid()).orderByChild(name);
        }else if (type == Constants.APPOINTMENTTYPE){
            query = database.child(Constants.APPOINTMENTS).child(auth.getCurrentUser().getUid()).orderByChild(name);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap: dataSnapshot.getChildren()){
                    String key = dataSnap.getKey();
                    String bookingKey = database.child(Constants.BOOKINGS).child(auth.getCurrentUser().getUid()).child(key).push().getKey();
                    Map<String, Object> eventMap = new HashMap<>();

                    eventMap.put(Constants.BOOKINGSPATH + auth.getCurrentUser().getUid() + "/" + key + "/" + bookingKey, booking);
                    eventMap.put(Constants.DATESPATH + auth.getCurrentUser().getUid() + "/" + bookingKey, booking.getDates());

                    database.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Ramy", "SUCCESS");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Ramy", "FAILURE");
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Ramy", databaseError.getMessage());
            }
        });
    }
}
