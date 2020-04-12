package com.example.pointme.backendCommunications;

import androidx.annotation.NonNull;
import android.util.Log;
import android.util.Range;

import com.example.pointme.interfaces.BookingFragmentDBInt;
import com.example.pointme.interfaces.CategoriesFragmentDBInt;
import com.example.pointme.interfaces.CheckBookerFreeDBInt;
import com.example.pointme.interfaces.ClientBookingsDBInt;
import com.example.pointme.interfaces.DatePickerDBInt;
import com.example.pointme.interfaces.FavoritesFragmentDBInt;
import com.example.pointme.interfaces.ListOfSPFragmentDBInt;
import com.example.pointme.interfaces.EventsFragmentDBInt;
import com.example.pointme.interfaces.WriteDBInt;
import com.example.pointme.constants.DBWriteType;
import com.example.pointme.constants.ServerResult;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Booking;
import com.example.pointme.constants.Constants;
import com.example.pointme.models.Event;
import com.example.pointme.utils.Helper;
import com.example.pointme.models.ProfileInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DBCom {

    public static void getCategoriesList(final CategoriesFragmentDBInt categoriesFragmentDBInt) {
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<String> servicesList = new ArrayList<>();
//
//                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                    servicesList.add(dataSnap.getKey());
//                }
//                categoriesFragmentDBInt.setCategoriesList(servicesList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void getSPList(final ListOfSPFragmentDBInt listOfSPFragmentDBInt, String title) {
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child("ServiceProviders").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<String> sPList = new ArrayList<>();
//
//                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                    sPList.add(dataSnap.getKey());
//                }
//                //listOfSPFragmentDBInt.setSPList(sPList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void getProfile(final EventsFragmentDBInt eventsFragmentDBInt, String name) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.USERS).orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String uid = "";
//                ProfileInfo profile = null;
//                for(DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                    uid = dataSnap.getKey();
//                    profile = dataSnapshot.getValue(ProfileInfo.class);
//                    profile.setKey(uid);
//                }
//                final ArrayList<Event> eventsList = new ArrayList<>();
//                final ProfileInfo profile2 = profile;
//                mDatabase.child(Constants.EVENTS).orderByChild("spId").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                            Event event = dataSnap.getValue(Event.class);
//                            event.setKey(dataSnap.getKey());
//                            eventsList.add(event);
//                        }
//                        //eventsFragmentDBInt.setProfile(profile2, eventsList);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void getProfilesByService(final ListOfSPFragmentDBInt listOfSPFragmentDBInt, String service){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final ArrayList<ProfileInfo> profilesList = new ArrayList<>();
//
//        mDatabase.child(Constants.USERS).orderByChild("ser").equalTo(service).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
//                        ProfileInfo profile = dataSnap.getValue(ProfileInfo.class);
//                        profile.setKey(dataSnap.getKey());
//                        profilesList.add(profile);
//                    }
//                }
//                listOfSPFragmentDBInt.setSPList(ServerResult.SUCCESS, profilesList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                listOfSPFragmentDBInt.setSPList(ServerResult.FAILURE, null);
//            }
//        });
    }

    public static void getSPEventsAndAppointments(final EventsFragmentDBInt eventsFragmentDBInt, final String spID){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final ArrayList<Event> eventsList = new ArrayList<>();
//        final ArrayList<Appointment> appointmentsList = new ArrayList<>();
//
//        mDatabase.child(Constants.EVENTS).orderByChild("spId").equalTo(spID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()) {
//                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                        Event event = dataSnap.getValue(Event.class);
//                        event.setKey(dataSnap.getKey());
//                        eventsList.add(event);
//                    }
//                }
//                mDatabase.child(Constants.APPOINTMENTS).orderByChild("spId").equalTo(spID).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()) {
//                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                                Appointment appointment = dataSnap.getValue(Appointment.class);
//                                appointment.setKey(dataSnap.getKey());
//                                appointmentsList.add(appointment);
//                            }
//                        }
//                        eventsFragmentDBInt.setSPEventsAndAppointments(ServerResult.SUCCESS, eventsList, appointmentsList);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        eventsFragmentDBInt.setSPEventsAndAppointments(ServerResult.FAILURE, null, null);
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                eventsFragmentDBInt.setSPEventsAndAppointments(ServerResult.FAILURE, null, null);
//            }
//        });
    }

//    public static StorageReference getProfilePicRef(String spID) {
//        return FirebaseStorage.getInstance().getReference().child(Constants.USERS).child(spID + Constants.JPG_EXT);
//
//    }

    public static void getEventsBySPID(String spID){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final ArrayList<Event> eventsList = new ArrayList<>();
//
//        mDatabase.child(Constants.EVENTS).orderByChild("spId").equalTo(spID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnap: dataSnapshot.getChildren()){
//                    Event event = dataSnap.getValue(Event.class);
//                    event.setKey(dataSnap.getKey());
//                    eventsList.add(event);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void addFavorite(final WriteDBInt writeDBInt, String spID){
//      [  final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        mDatabase.child(Constants.FAVORITES).child(mAuth.getCurrentUser().getUid()).child(spID).setValue(true)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        writeDBInt.writeToDBResult(ServerResult.SUCCESS, DBWriteType.FAVORITES);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        writeDBInt.writeToDBResult(ServerResult.FAILURE, DBWriteType.FAVORITES);
//                    }
//                });]
    }

    public static void removeFavorite(final WriteDBInt writeDBInt, String spID){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        mDatabase.child(Constants.FAVORITES).child(mAuth.getCurrentUser().getUid()).child(spID).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        writeDBInt.writeToDBResult(ServerResult.SUCCESS, DBWriteType.FAVORITES);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                writeDBInt.writeToDBResult(ServerResult.FAILURE, DBWriteType.FAVORITES);
//            }
//        });
    }

    public static void getUserFavorites(final FavoritesFragmentDBInt favoritesFragmentDBInt, final WriteDBInt writeDBInt){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final ArrayList<ProfileInfo> profilesList = new ArrayList<>();
//
//        mDatabase.child(Constants.FAVORITES).child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final ArrayList<String> spIDList = new ArrayList<>();
//                if(dataSnapshot.exists()){
//                    for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
//                        spIDList.add(dataSnap.getKey());
//                    }
//                    final String lastSpID = spIDList.get(spIDList.size() - 1);
//                    for(final String spID : spIDList){
//                        mDatabase.child(Constants.USERS).child(spID).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                ProfileInfo profile = dataSnapshot.getValue(ProfileInfo.class);
//                                profile.setKey(dataSnapshot.getKey());
//                                profilesList.add(profile);
//                                if(spID.equals(lastSpID)){
//                                    favoritesFragmentDBInt.setFavorites(ServerResult.SUCCESS, profilesList);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                favoritesFragmentDBInt.setFavorites(ServerResult.FAILURE, null);
//                            }
//                        });
//                    }
//                }else{
//                    favoritesFragmentDBInt.setFavorites(ServerResult.SUCCESS, profilesList);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                favoritesFragmentDBInt.setFavorites(ServerResult.FAILURE, null);
//            }
//        });
    }

    public static void checkBookerFree(final CheckBookerFreeDBInt checkBookerFreeDBInt, final String datetime){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        mDatabase.child(Constants.BOOKER_SCHEDULE).child(mAuth.getCurrentUser().getUid()).orderByKey()
//                .startAt(datetime.substring(0, 8)).endAt(datetime.substring(0, 8) + Constants.UNICODE_END_CHARACTER)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            for(DataSnapshot datasnap : dataSnapshot.getChildren()){
//                                ArrayList<Range<String>> busyRanges = Helper.getBusyRanges(datasnap.getKey(), null);
//                                String startTime = datetime.substring(8, 12);
//                                String endTime = datetime.substring(12, 16);
//                                if(Helper.isTimeInRangeList(startTime, busyRanges) || Helper.isTimeInRangeList(endTime, busyRanges)){
//                                    checkBookerFreeDBInt.isBookerFree(false);
//                                }else{
//                                    checkBookerFreeDBInt.isBookerFree(true);
//                                }
//                            }
//                        }else{
//                            checkBookerFreeDBInt.isBookerFree(true);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
    }

    public static void getAppScheduleAndScheduleWB(final DatePickerDBInt datePickerDBInt, final String uid){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final ArrayList<String> appSchedule = new ArrayList<>();
//        final ArrayList<String> scheduleWB = new ArrayList<>();
//
//        mDatabase.child(Constants.APPOINTMENT_SCHEDULE).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()) {
//                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                        appSchedule.add(dataSnap.getKey());
//                    }
//                }
//                mDatabase.child(Constants.SCHEDULE_WB).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
//                                scheduleWB.add(dataSnap.getKey());
//                            }
//                        }
//                        datePickerDBInt.setAppScheduleAndScheduleWB(appSchedule, scheduleWB);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void getAppBookingSlots(final DatePickerDBInt datePickerDBInt, final String spID, final String yearMonth,
                                          final ArrayList<String> appSchedule, final ArrayList<String> scheduleWB, final String duration, final String timeD){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        final ArrayList<String> monthDates = new ArrayList<>();
//        final HashMap<Integer, ArrayList<String>> scheduleDBMap = new HashMap<>();
//        final HashMap<String, ArrayList<String>> scheduleWBMap = new HashMap<>();
//
//        mDatabase.child(Constants.SCHEDULE_DB).child(spID).orderByKey().startAt(yearMonth).endAt(yearMonth + Constants.UNICODE_END_CHARACTER)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
//                                monthDates.add(dataSnap.getKey());
//                            }
//                            for(String date : monthDates){
//                                CalendarDay day = CalendarDay.from(Integer.parseInt(date.substring(0, 4)),
//                                        Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
//                                final String weekDay = Helper.getWeekDay(day);
//                                String weekSchedule = "";
//                                for(String schedule : appSchedule){
//                                    if(schedule.startsWith(weekDay)){
//                                        weekSchedule = schedule;
//                                        break;
//                                    }
//                                }
//                                ArrayList<String> arrayList = Helper.getBookingSlots(date, null, weekSchedule, duration, timeD);
//                                scheduleDBMap.put(day.getDay(), arrayList);
//                            }
//                        }else{
//                            for(String schedule : appSchedule){
//                                String day = null;
//                                String weekDay = schedule.substring(0, 2);
//                                for(String wb : scheduleWB) {
//                                    if (wb.startsWith(weekDay)) {
//                                        day = wb;
//                                        break;
//                                    }
//                                }
//                                ArrayList<String> arrayList = Helper.getBookingSlots(null, day, schedule, duration, timeD);
//                                scheduleWBMap.put(weekDay, arrayList);
//                            }
//                        }
//                        datePickerDBInt.setAppBookingSlots(scheduleDBMap, scheduleWBMap);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
    }

    public static void getFreeAppBookingSlots(final String spID, final String date, final String appSchedule,
                                              final String duration, final String timeD) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.SCHEDULE_DB).child(spID).orderByKey().startAt(date).endAt(date + Constants.UNICODE_END_CHARACTER)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String scheduleDB = null;
//                        if (dataSnapshot.exists()) {
//                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                                scheduleDB = dataSnap.getKey();
//                            }
//                            ArrayList<String> arrayList = Helper.getBookingSlots(scheduleDB, null, appSchedule, duration, timeD);
//                        } else {
//                            CalendarDay day = CalendarDay.from(Integer.parseInt(date.substring(0, 4)),
//                                    Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
//                            final String weekDay = Helper.getWeekDay(day);
//                            mDatabase.child(Constants.SCHEDULE_WB).child(spID).orderByKey().startAt(weekDay)
//                                    .endAt(weekDay + Constants.UNICODE_END_CHARACTER).addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            String scheduleWB = null;
//                                            if (dataSnapshot.exists()) {
//                                                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                                                    scheduleWB = dataSnap.getKey();
//                                                }
//                                                ArrayList<String> arrayList = Helper.getBookingSlots(null, scheduleWB, appSchedule,
//                                                        duration, timeD);
//                                            } else {
//                                                ArrayList<String> arrayList = Helper.getBookingSlots(null, null, appSchedule,
//                                                        duration, timeD);
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
    }

    public static void uploadEvent(Event event) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        String eventKey = mDatabase.child(Constants.EVENTS).push().getKey();
//        Map<String, Object> eventMap = new HashMap<>();
//
//        eventMap.put(Constants.EVENTSPATH + eventKey, event);
//        eventMap.put(Constants.DATESPATH + eventKey, event.getDates());
//
//        mDatabase.updateChildren(eventMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }

    public static void uploadBooking(final BookingFragmentDBInt bookingFragmentDBInt, final Booking booking) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.BOOKINGS).push().setValue(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }

    public static void fetchBookingByID(String uid) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.BOOKINGS).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Booking b = dataSnapshot.getValue(Booking.class);
//                Log.d("romeo", b.getCrId());
//                //Log.d("romeo", b.getDatesAsList().get(0));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void getClientBookings(final ClientBookingsDBInt clientBookingsDBInt, String creatorID) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.BOOKINGS).orderByChild("crId").equalTo(creatorID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<Booking> bookingsList = new ArrayList<>();
//                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                    bookingsList.add(dataSnap.getValue(Booking.class));
//                }
//                Collections.sort(bookingsList);
//                clientBookingsDBInt.setClientBookings(ServerResult.SUCCESS, bookingsList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                clientBookingsDBInt.setClientBookings(ServerResult.FAILURE, null);
//            }
//        });
    }

    public static void fetchBookingBySPID(String spID) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.BOOKINGS).orderByChild("spId").equalTo(spID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<Booking> bookingsList = new ArrayList<>();
//                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                    bookingsList.add(dataSnap.getValue(Booking.class));
//                }
//                Log.d("romeo", bookingsList.get(0).getTime());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public static void fetchBookingByEventID(String eventID) {
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(Constants.BOOKINGS).orderByChild("evId").equalTo(eventID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<Booking> bookingsList = new ArrayList<>();
//                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
//                    bookingsList.add(dataSnap.getValue(Booking.class));
//                }
//                Log.d("romeo", bookingsList.get(0).getTime());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
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

}
