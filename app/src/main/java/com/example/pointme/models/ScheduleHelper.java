package com.example.pointme.models;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.pointme.constants.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ScheduleHelper {

    public static HashMap<String, Boolean> convertDateListToMap(ArrayList<String> dates){
        HashMap<String, Boolean> map = new HashMap<>();
        for (String date: dates){
            map.put(date, true);
        }
        return map;
    }

    public static void uploadEvent(FirebaseAuth auth, DatabaseReference database, Event event){
        String eventKey = database.child(Constants.EVENTS).child(auth.getCurrentUser().getUid()).push().getKey();
        Map<String, Object> eventMap = new HashMap<>();

        eventMap.put(Constants.EVENTSPATH + auth.getCurrentUser().getUid() + "/" + eventKey, event);
        eventMap.put(Constants.DATESPATH + auth.getCurrentUser().getUid() + "/" + eventKey, event.getDates());

        database.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Event created succesfully!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void uploadAppointment(FirebaseAuth auth, DatabaseReference database, Appointment appointment){
        String appointmentKey = database.child(Constants.APPOINTMENTS).child(auth.getCurrentUser().getUid()).push().getKey();
        Map<String, Object> eventMap = new HashMap<>();

        eventMap.put(Constants.APPOINTMENTSPATH + auth.getCurrentUser().getUid() + "/" + appointmentKey, appointment);
        //eventMap.put(Constants.DATESPATH + auth.getCurrentUser().getUid() + "/" + appointmentKey, convertDateListToMap(appointment.getDates()));

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
                    String duration = dataSnap.child("dur").getValue(String.class);
                    ArrayList<String> tempDates = new ArrayList<>();
                    /*for (String s: booking.getDate()){
                        PointmeDate date = PointmeDate.StringToDate(s);
                        date.addDuration(duration);
                        String tempDate = date.toString();
                        tempDates.add(tempDate);
                    }
                    booking.setDatesFromList(tempDates);*/
                    String bookingKey = database.child(Constants.BOOKINGS).child(auth.getCurrentUser().getUid()).child(key).push().getKey();
                    Map<String, Object> eventMap = new HashMap<>();

                    eventMap.put(Constants.BOOKINGSPATH + auth.getCurrentUser().getUid() + "/" + key + "/" + bookingKey, booking);
                    eventMap.put(Constants.DATESPATH + auth.getCurrentUser().getUid() + "/" + bookingKey, booking.getDate());

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
