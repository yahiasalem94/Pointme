package com.example.pointme.classes;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    public static final int EVENTTYPE = 1;
    public static final int APPOINTMENTTYPE = 2;
    public static final String EVENTS = "Events";
    public static final String APPOINTMENTS = "Appointments";
    public static final String BOOKINGS = "Bookings";
    public static final String DATES = "Dates";
    public static final String EVENTSPATH = "/Events/";
    public static final String APPOINTMENTSPATH = "/Appointments/";
    public static final String BOOKINGSPATH = "/Bookings/";
    public static final String DATESPATH = "/Dates/";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SUCCESS, FAILURE})
    public @interface ServerResult { }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EVENTTYPE, APPOINTMENTTYPE})
    public @interface ScheduleType { }
}
