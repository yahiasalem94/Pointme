package com.example.pointme.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public static final int EVENTTYPE = 1;
    public static final int APPOINTMENTTYPE = 2;
    public static final String USERS = "Users";
    public static final String EVENTS = "Events";
    public static final String APPOINTMENTS = "Appointments";
    public static final String BOOKINGS = "Bookings";
    public static final String APPOINTMENTSCHEDULE = "AppointmentSchedule";
    public static final String SCHEDULEDB = "ScheduleDB";
    public static final String SCHEDULEWB = "ScheduleWB";
    public static final String BOOKERSCHEDULE = "BookerSchedule";
    public static final String UNICODE_END_CHARACTER = "\uf8ff";
    public static final String DATES = "Dates";
    public static final String EVENTSPATH = "/Events/";
    public static final String APPOINTMENTSPATH = "/Appointments/";
    public static final String BOOKINGSPATH = "/Bookings/";
    public static final String DATESPATH = "/Dates/";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ServerResult.SUCCESS, ServerResult.FAILURE})
    public @interface ServerResult {
        int SUCCESS = 1;
        int FAILURE = 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ApprovalStatus.DECLINED, ApprovalStatus.PENDING, ApprovalStatus.APPROVED})
    public @interface ApprovalStatus {
        int DECLINED = -1;
        int PENDING = 0;
        int APPROVED = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EVENTTYPE, APPOINTMENTTYPE})
    public @interface ScheduleType { }
}
