package com.example.pointme.backendCommunications;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Range;

import com.example.pointme.Interfaces.BookingFragmentDBInt;
import com.example.pointme.Interfaces.CategoriesFragmentDBInt;
import com.example.pointme.Interfaces.CheckBookerFreeDBInt;
import com.example.pointme.Interfaces.DatePickerDBInt;
import com.example.pointme.Interfaces.ListOfSPFragmentDBInt;
import com.example.pointme.Interfaces.ProfileFragmentDBInt;
import com.example.pointme.models.Booking;
import com.example.pointme.models.Constants;
import com.example.pointme.models.Event;
import com.example.pointme.models.Helper;
import com.example.pointme.models.PointmeDate;
import com.example.pointme.models.ProfileInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBCom {

    public static void getCategoriesList(final CategoriesFragmentDBInt categoriesFragmentDBInt) {
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

    public static void getSPList(final ListOfSPFragmentDBInt listOfSPFragmentDBInt, String title) {
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

    public static void getProfile(final ProfileFragmentDBInt profileFragmentDBInt, String name) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.USERS).orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = "";
                ProfileInfo profile = null;
                for(DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    uid = dataSnap.getKey();
                    profile = dataSnapshot.getValue(ProfileInfo.class);
                    profile.setKey(uid);
                }
                final ArrayList<Event> eventsList = new ArrayList<>();
                final ProfileInfo profile2 = profile;
                mDatabase.child(Constants.EVENTS).orderByChild("spId").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                            Event event = dataSnap.getValue(Event.class);
                            event.setKey(dataSnap.getKey());
                            eventsList.add(event);
                        }
                        profileFragmentDBInt.setProfile(profile2, eventsList);
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

    public static void checkBookerFree(final CheckBookerFreeDBInt checkBookerFreeDBInt, final String datetime){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mDatabase.child(Constants.BOOKERSCHEDULE).child(mAuth.getCurrentUser().getUid()).orderByKey()
                .startAt(datetime.substring(0, 8)).endAt(datetime.substring(0, 8) + Constants.UNICODE_END_CHARACTER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot datasnap : dataSnapshot.getChildren()){
                                ArrayList<Range<String>> busyRanges = Helper.getBusyRanges(datasnap.getKey(), null);
                                String startTime = datetime.substring(8, 12);
                                String endTime = datetime.substring(12, 16);
                                if(Helper.isTimeInRangeList(startTime, busyRanges) || Helper.isTimeInRangeList(endTime, busyRanges)){
                                    checkBookerFreeDBInt.isBookerFree(false);
                                }else{
                                    checkBookerFreeDBInt.isBookerFree(true);
                                }
                            }
                        }else{
                            checkBookerFreeDBInt.isBookerFree(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public static void getAppScheduleAndScheduleWB(final DatePickerDBInt datePickerDBInt, final String uid){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<String> appSchedule = new ArrayList<>();
        final ArrayList<String> scheduleWB = new ArrayList<>();

        mDatabase.child(Constants.APPOINTMENTSCHEDULE).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        appSchedule.add(dataSnap.getKey());
                    }
                }
                mDatabase.child(Constants.SCHEDULEWB).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                                scheduleWB.add(dataSnap.getKey());
                            }
                        }
                        datePickerDBInt.setAppScheduleAndScheduleWB(appSchedule, scheduleWB);
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

    public static void getAppBookingSlots(final DatePickerDBInt datePickerDBInt, final String spID, final String yyyymm,
                                          final ArrayList<String> appSchedule, final ArrayList<String> scheduleWB, final String duration, final String timeD){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<String> monthDates = new ArrayList<>();
        final HashMap<Integer, ArrayList<String>> scheduleDBMap = new HashMap<>();
        final HashMap<String, ArrayList<String>> scheduleWBMap = new HashMap<>();

        mDatabase.child(Constants.SCHEDULEDB).child(spID).orderByKey().startAt(yyyymm).endAt(yyyymm + Constants.UNICODE_END_CHARACTER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                                monthDates.add(dataSnap.getKey());
                            }
                            for(String date : monthDates){
                                CalendarDay day = CalendarDay.from(Integer.parseInt(date.substring(0, 4)),
                                        Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
                                final String weekDay = Helper.getWeekDay(day);
                                String weekSchedule = "";
                                for(String schedule : appSchedule){
                                    if(schedule.startsWith(weekDay)){
                                        weekSchedule = schedule;
                                        break;
                                    }
                                }
                                ArrayList<String> arrayList = Helper.getBookingSlots(date, null, weekSchedule, duration, timeD);
                                scheduleDBMap.put(day.getDay(), arrayList);
                            }
                        }else{
                            for(String schedule : appSchedule){
                                String day = null;
                                String weekDay = schedule.substring(0, 2);
                                for(String wb : scheduleWB) {
                                    if (wb.startsWith(weekDay)) {
                                        day = wb;
                                        break;
                                    }
                                }
                                ArrayList<String> arrayList = Helper.getBookingSlots(null, day, schedule, duration, timeD);
                                scheduleWBMap.put(weekDay, arrayList);
                            }
                        }
                        datePickerDBInt.setAppBookingSlots(scheduleDBMap, scheduleWBMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public static void getFreeAppBookingSlots(final String spID, final String date, final String appSchedule,
                                              final String duration, final String timeD) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.SCHEDULEDB).child(spID).orderByKey().startAt(date).endAt(date + Constants.UNICODE_END_CHARACTER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String scheduleDB = null;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                scheduleDB = dataSnap.getKey();
                            }
                            ArrayList<String> arrayList = Helper.getBookingSlots(scheduleDB, null, appSchedule, duration, timeD);
                        } else {
                            CalendarDay day = CalendarDay.from(Integer.parseInt(date.substring(0, 4)),
                                    Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
                            final String weekDay = Helper.getWeekDay(day);
                            mDatabase.child(Constants.SCHEDULEWB).child(spID).orderByKey().startAt(weekDay)
                                    .endAt(weekDay + Constants.UNICODE_END_CHARACTER).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String scheduleWB = null;
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                                    scheduleWB = dataSnap.getKey();
                                                }
                                                ArrayList<String> arrayList = Helper.getBookingSlots(null, scheduleWB, appSchedule,
                                                        duration, timeD);
                                            } else {
                                                ArrayList<String> arrayList = Helper.getBookingSlots(null, null, appSchedule,
                                                        duration, timeD);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public static void uploadEvent(Event event) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        String eventKey = mDatabase.child(Constants.EVENTS).push().getKey();
        Map<String, Object> eventMap = new HashMap<>();

        eventMap.put(Constants.EVENTSPATH + eventKey, event);
        eventMap.put(Constants.DATESPATH + eventKey, event.getDates());

        mDatabase.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void uploadBooking(final BookingFragmentDBInt bookingFragmentDBInt, final Booking booking) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.BOOKINGS).push().setValue(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void fetchBookingByID(String uid) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.BOOKINGS).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Booking b = dataSnapshot.getValue(Booking.class);
                Log.d("romeo", b.getCrId());
                //Log.d("romeo", b.getDatesAsList().get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void fetchBookingByCreatorID(String creatorID) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.BOOKINGS).orderByChild("crId").equalTo(creatorID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Booking> bookingsList = new ArrayList<>();
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    bookingsList.add(dataSnap.getValue(Booking.class));
                }
                Log.d("romeo", bookingsList.get(0).getEvId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void fetchBookingBySPID(String spID) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.BOOKINGS).orderByChild("spId").equalTo(spID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Booking> bookingsList = new ArrayList<>();
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    bookingsList.add(dataSnap.getValue(Booking.class));
                }
                Log.d("romeo", bookingsList.get(0).getEvId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void fetchBookingByEventID(String eventID) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child(Constants.BOOKINGS).orderByChild("evId").equalTo(eventID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Booking> bookingsList = new ArrayList<>();
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    bookingsList.add(dataSnap.getValue(Booking.class));
                }
                Log.d("romeo", bookingsList.get(0).getEvId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*public static void uploadBooking(final BookingFragmentDBInt bookingFragmentDBInt, @Constants.ScheduleType int type, String name, final Booking booking) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Query query = null;

        if (type == Constants.EVENTTYPE) {
            query = mDatabase.child(Constants.EVENTS).child(mAuth.getCurrentUser().getUid()).orderByChild(name);
        } else if (type == Constants.APPOINTMENTTYPE) {
            query = mDatabase.child(Constants.APPOINTMENTS).child(mAuth.getCurrentUser().getUid()).orderByChild(name);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    String key = dataSnap.getKey();
                    String duration = dataSnap.child("dur").getValue(String.class);
                    ArrayList<String> tempDates = new ArrayList<>();
                    for (String s : booking.getDatesAsList()) {
                        PointmeDate date = PointmeDate.StringToDate(s);
                        date.addDuration(duration);
                        String tempDate = date.toString();
                        tempDates.add(tempDate);
                    }
                    booking.setDatesFromList(tempDates);
                    String bookingKey = mDatabase.child(Constants.BOOKINGS).child(mAuth.getCurrentUser().getUid()).child(key).push().getKey();
                    Map<String, Object> eventMap = new HashMap<>();

                    eventMap.put(Constants.BOOKINGSPATH + mAuth.getCurrentUser().getUid() + "/" + key + "/" + bookingKey, booking);
                    eventMap.put(Constants.DATESPATH + mAuth.getCurrentUser().getUid() + "/" + bookingKey, booking.getDates());

                    mDatabase.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            bookingFragmentDBInt.uploadSuccessful(true);
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
    }*/

    public static HashMap<String, Boolean> convertDateListToMap(ArrayList<String> dates) {
        HashMap<String, Boolean> map = new HashMap<>();
        for (String date : dates) {
            map.put(date, true);
        }
        return map;
    }

    public static ArrayList<String> convertMapToDateList(HashMap<String, Boolean> map) {
        return new ArrayList<>(map.keySet());
    }
}
